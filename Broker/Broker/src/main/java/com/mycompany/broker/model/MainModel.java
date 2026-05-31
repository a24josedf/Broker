package com.mycompany.broker.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainModel implements Serializable {

    public interface MarketListener {
        void onMarketChanged();
    }

    private static final long serialVersionUID = 1L;
    private static final File DATA_FILE = new File("data/market-state.ser");

    private ArrayList<Agent> agents = new ArrayList<>();
    private ArrayList<Operation> operations = new ArrayList<>();
    private ArrayList<Trade> trades = new ArrayList<>();
    private ArrayList<Double> prices = new ArrayList<>();
    private double currentPrice = 100.0;
    private double maxPrice = 100.0;
    private double minPrice = 100.0;
    private int volume = 0;
    private boolean demoMode = false;

    private transient Object lock;
    private transient CopyOnWriteArrayList<MarketListener> listeners;
    private transient Thread matchThread;
    private transient Thread graphThread;
    private transient Thread demoThread;
    private transient boolean running;

    public MainModel() {
        MainModel loaded = load();
        if (loaded != null) {
            copyFrom(loaded);
        }
        initializeTransients();
        addDemoDataIfEmpty();
        rebuildAgentOrderReferences();
        Agent.refreshNextId(agents);
        Operation.refreshNextId(operations);
        if (prices.isEmpty()) {
            prices.add(currentPrice);
        }
        startMatchingEngine();
        startDemoFlow();
    }

    private void addDemoDataIfEmpty() {
        if (!agents.isEmpty()) {
            return;
        }

        Agent alice = addDemoAgent("Alice", 12000.0, 25);
        Agent bob = addDemoAgent("Bob", 8500.0, 40);
        Agent carol = addDemoAgent("Carol", 9500.0, 32);
        Agent dan = addDemoAgent("Dan", 7000.0, 28);
        Agent emma = addDemoAgent("Emma", 15000.0, 18);
        Agent frank = addDemoAgent("Frank", 6200.0, 45);
        Agent grace = addDemoAgent("Grace", 11000.0, 22);
        Agent henry = addDemoAgent("Henry", 7800.0, 36);
        Agent iris = addDemoAgent("Iris", 13200.0, 16);
        Agent jack = addDemoAgent("Jack", 5400.0, 50);
        Agent kim = addDemoAgent("Kim", 9000.0, 24);
        Agent leo = addDemoAgent("Leo", 10200.0, 30);

        addDemoOrder(alice, Operation.TYPE_BUY, 92.0, 6);
        addDemoOrder(bob, Operation.TYPE_SELL, 124.0, 4);
        addDemoOrder(carol, Operation.TYPE_BUY, 94.0, 5);
        addDemoOrder(dan, Operation.TYPE_SELL, 122.0, 7);
        addDemoOrder(emma, Operation.TYPE_BUY, 91.0, 8);
        addDemoOrder(frank, Operation.TYPE_SELL, 126.0, 6);
        addDemoOrder(grace, Operation.TYPE_BUY, 93.0, 5);
        addDemoOrder(henry, Operation.TYPE_SELL, 128.0, 7);
        addDemoOrder(iris, Operation.TYPE_BUY, 90.0, 3);
        addDemoOrder(jack, Operation.TYPE_SELL, 123.0, 9);
        addDemoOrder(kim, Operation.TYPE_BUY, 89.0, 4);
        addDemoOrder(leo, Operation.TYPE_SELL, 125.0, 5);

        prices.add(currentPrice);
        demoMode = true;
    }

    private Agent addDemoAgent(String name, double balance, int stock) {
        Agent agent = new Agent(name, balance, stock);
        agents.add(agent);
        return agent;
    }

    private void addDemoOrder(Agent agent, String type, double price, int quantity) {
        Operation order = new Operation(agent, type, price, quantity);
        agent.setOperation(order);
        operations.add(order);
    }

    private void initializeTransients() {
        lock = new Object();
        listeners = new CopyOnWriteArrayList<>();
    }

    private void copyFrom(MainModel loaded) {
        agents = loaded.agents;
        operations = loaded.operations;
        trades = loaded.trades;
        prices = loaded.prices;
        currentPrice = loaded.currentPrice;
        maxPrice = loaded.maxPrice;
        minPrice = loaded.minPrice;
        volume = loaded.volume;
        demoMode = loaded.demoMode;
        if (agents == null) {
            agents = new ArrayList<>();
        }
        if (operations == null) {
            operations = new ArrayList<>();
        }
        if (trades == null) {
            trades = new ArrayList<>();
        }
        if (prices == null) {
            prices = new ArrayList<>();
        }
        if (currentPrice <= 0) {
            currentPrice = 100.0;
        }
        if (maxPrice <= 0) {
            maxPrice = currentPrice;
        }
        if (minPrice <= 0) {
            minPrice = currentPrice;
        }
    }

    private MainModel load() {
        if (!DATA_FILE.exists() || DATA_FILE.length() == 0) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (MainModel) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("No se pudo cargar el estado anterior: " + ex.getMessage());
            return null;
        }
    }

    private void rebuildAgentOrderReferences() {
        long fallbackId = 1L;
        for (Agent agent : agents) {
            agent.setPurchaseOperation(null);
            agent.setSaleOperation(null);
        }
        operations.removeIf(operation -> operation.getAgent() == null
                || operation.isCompleted()
                || (!Operation.TYPE_BUY.equals(operation.getType()) && !Operation.TYPE_SELL.equals(operation.getType())));
        for (Operation operation : operations) {
            operation.setIdIfMissing(fallbackId++);
            operation.getAgent().setOperation(operation);
        }
    }

    public void addListener(MarketListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (MarketListener listener : listeners) {
            listener.onMarketChanged();
        }
    }

    public void addAgent(String name, double balance, int stock) {
        synchronized (lock) {
            agents.add(new Agent(name, balance, stock));
            save();
        }
        notifyListeners();
    }

    public void resetDemoData() {
        synchronized (lock) {
            agents.clear();
            operations.clear();
            trades.clear();
            prices.clear();
            currentPrice = 100.0;
            maxPrice = 100.0;
            minPrice = 100.0;
            volume = 0;
            demoMode = true;
            addDemoDataIfEmpty();
            Agent.refreshNextId(agents);
            Operation.refreshNextId(operations);
            save();
        }
        startDemoFlow();
        notifyListeners();
    }

    public boolean nameExists(String name) {
        synchronized (lock) {
            return agents.stream().anyMatch(agent -> agent.getName().equalsIgnoreCase(name));
        }
    }

    public void submitOperation(Agent agent, String type, double price, int quantity) {
        synchronized (lock) {
            Operation previous = agent.getOperation(type);
            if (previous != null) {
                operations.remove(previous);
            }
            Operation operation = new Operation(agent, type, price, quantity);
            agent.setOperation(operation);
            operations.add(operation);
            save();
        }
        notifyListeners();
    }

    public void cancelOperation(Agent agent, String type) {
        synchronized (lock) {
            Operation operation = agent.getOperation(type);
            if (operation != null) {
                operations.remove(operation);
                agent.clearOperation(type);
                save();
            }
        }
        notifyListeners();
    }

    public ArrayList<Agent> getAgents() {
        synchronized (lock) {
            return new ArrayList<>(agents);
        }
    }

    public Agent getAgentAt(int row) {
        synchronized (lock) {
            return agents.get(row);
        }
    }

    public ArrayList<Operation> getOperations() {
        synchronized (lock) {
            return new ArrayList<>(operations);
        }
    }

    public Operation getOperationById(long id) {
        synchronized (lock) {
            return operations.stream()
                    .filter(operation -> operation.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
    }

    public ArrayList<Trade> getTrades() {
        synchronized (lock) {
            return new ArrayList<>(trades);
        }
    }

    public List<Double> getPrices() {
        synchronized (lock) {
            return new ArrayList<>(prices);
        }
    }

    public double getCurrentPrice() {
        synchronized (lock) {
            return currentPrice;
        }
    }

    public double getSessionMax() {
        synchronized (lock) {
            return maxPrice;
        }
    }

    public double getSessionMin() {
        synchronized (lock) {
            return minPrice;
        }
    }

    public int getTradedVolume() {
        synchronized (lock) {
            return volume;
        }
    }

    public int getExecutedTradesCount() {
        synchronized (lock) {
            return trades.size();
        }
    }

    public void startMatchingEngine() {
        running = true;
        matchThread = new Thread(() -> {
            while (running) {
                boolean changed = matchOrders();
                if (changed) {
                    notifyListeners();
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }, "order-matcher");
        matchThread.setDaemon(true);
        matchThread.start();

        graphThread = new Thread(() -> {
            while (running) {
                synchronized (lock) {
                    prices.add(currentPrice);
                    if (prices.size() > 2000) {
                        prices.remove(0);
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }, "graph-updater");
        graphThread.setDaemon(true);
        graphThread.start();
    }

    private void startDemoFlow() {
        if (!demoMode || demoThread != null && demoThread.isAlive()) {
            return;
        }

        demoThread = new Thread(() -> {
            double[] targetPrices = {104.0, 97.0, 111.0, 94.0, 108.0, 99.0, 115.0, 96.0};
            int index = 0;
            while (running && demoMode) {
                try {
                    Thread.sleep(1800);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
                addDemoCross(targetPrices[index % targetPrices.length], 2 + index % 4);
                index++;
            }
        }, "demo-order-flow");
        demoThread.setDaemon(true);
        demoThread.start();
    }

    private void addDemoCross(double price, int quantity) {
        synchronized (lock) {
            if (agents.size() < 2) {
                return;
            }
            int buyerIndex = (int) ((System.currentTimeMillis() / 1800) % agents.size());
            Agent buyer = agents.get(buyerIndex);
            Agent seller = agents.get((buyerIndex + 1) % agents.size());
            if (seller.getStock() < quantity || buyer.getBalance() < (price + 2.0) * quantity) {
                return;
            }

            Operation oldBuy = buyer.getPurchaseOperation();
            Operation oldSell = seller.getSaleOperation();
            if (oldBuy != null) {
                operations.remove(oldBuy);
            }
            if (oldSell != null) {
                operations.remove(oldSell);
            }

            Operation buy = new Operation(buyer, Operation.TYPE_BUY, price + 2.0, quantity);
            Operation sell = new Operation(seller, Operation.TYPE_SELL, price, quantity);
            buyer.setPurchaseOperation(buy);
            seller.setSaleOperation(sell);
            operations.add(buy);
            operations.add(sell);
            save();
        }
        notifyListeners();
    }

    public void stopAndSave() {
        running = false;
        if (matchThread != null) {
            matchThread.interrupt();
        }
        if (graphThread != null) {
            graphThread.interrupt();
        }
        if (demoThread != null) {
            demoThread.interrupt();
        }
        synchronized (lock) {
            save();
        }
    }

    private boolean matchOrders() {
        synchronized (lock) {
            Operation[] match = bestMatch();
            if (match == null) {
                return false;
            }
            Operation buy = match[0];
            Operation sell = match[1];

            int quantity = Math.min(buy.getRemainingQuantity(), sell.getRemainingQuantity());
            double tradePrice = sell.getPrice();

            if (buy.getAgent().getBalance() < quantity * tradePrice || sell.getAgent().getStock() < quantity) {
                removeInvalidOperation(buy);
                removeInvalidOperation(sell);
                save();
                return true;
            }

            buy.getAgent().setBalance(buy.getAgent().getBalance() - quantity * tradePrice);
            buy.getAgent().setStock(buy.getAgent().getStock() + quantity);
            sell.getAgent().setBalance(sell.getAgent().getBalance() + quantity * tradePrice);
            sell.getAgent().setStock(sell.getAgent().getStock() - quantity);

            buy.consume(quantity);
            sell.consume(quantity);
            currentPrice = tradePrice;
            maxPrice = Math.max(maxPrice, currentPrice);
            minPrice = Math.min(minPrice, currentPrice);
            volume += quantity;
            prices.add(currentPrice);
            trades.add(new Trade(buy.getAgent().getName(), sell.getAgent().getName(), quantity, tradePrice));

            if (buy.isCompleted()) {
                operations.remove(buy);
                buy.getAgent().clearOperation(Operation.TYPE_BUY);
            }
            if (sell.isCompleted()) {
                operations.remove(sell);
                sell.getAgent().clearOperation(Operation.TYPE_SELL);
            }
            save();
            return true;
        }
    }

    private Operation[] bestMatch() {
        List<Operation> buys = operations.stream()
                .filter(operation -> Operation.TYPE_BUY.equals(operation.getType()))
                .sorted(Comparator.comparingDouble(Operation::getPrice).reversed()
                        .thenComparing(Operation::getCreatedAt))
                .toList();
        List<Operation> sells = operations.stream()
                .filter(operation -> Operation.TYPE_SELL.equals(operation.getType()))
                .sorted(Comparator.comparingDouble(Operation::getPrice)
                        .thenComparing(Operation::getCreatedAt))
                .toList();

        for (Operation buy : buys) {
            for (Operation sell : sells) {
                if (!buy.getAgent().equals(sell.getAgent()) && buy.getPrice() >= sell.getPrice()) {
                    return new Operation[]{buy, sell};
                }
            }
        }
        return null;
    }

    private void removeInvalidOperation(Operation operation) {
        operations.remove(operation);
        operation.getAgent().clearOperation(operation.getType());
    }

    private void save() {
        DATA_FILE.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
        } catch (IOException ex) {
            System.err.println("No se pudo guardar el estado: " + ex.getMessage());
        }
    }
}
