# Broker

Aplicacion Java Swing que simula un pequeno mercado bursatil sobre un unico
activo.

El proyecto esta orientado a la unidad de Servicios y Procesos, trabajando hilos,
sincronizacion, acceso seguro a datos compartidos y persistencia local.

## Funcionalidades

- Grafica del precio en tiempo real.
- Gestion de agentes con saldo y acciones.
- Ordenes limite de compra y venta.
- Maximo una orden de compra y una orden de venta por agente.
- Motor de casacion ejecutandose en segundo plano.
- Acceso sincronizado a los datos del mercado.
- Persistencia local en `data/market-state.ser`.
- Datos demo para ver el mercado funcionando automaticamente.

## Interfaz

- `Market`: muestra la grafica, precio actual, maximo, minimo, operaciones y volumen.
- `Agents`: permite crear agentes y seleccionar uno para gestionar sus ordenes.
- `Orders`: muestra ordenes activas y permite editarlas, cancelarlas o cargar datos demo.

## Funcionamiento

El motor de casacion se ejecuta en un hilo independiente. Compara ordenes de
compra y venta:

- Las compras se priorizan por precio mas alto.
- Las ventas se priorizan por precio mas bajo.
- Se ejecuta una operacion cuando una compra tiene precio mayor o igual que una venta.
- El precio del activo se actualiza con el precio de la ultima operacion ejecutada.

Los datos compartidos del mercado se protegen con sincronizacion para evitar
condiciones de carrera.

## Ejecucion

Compilar y ejecutar desde PowerShell:

```powershell
javac -d target\classes @(Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName })
java -cp target\classes com.mycompany.broker.Main
```

Si la aplicacion ya tiene datos guardados, se puede usar `Load demo data` en la
pestana `Orders` para reiniciar el mercado con agentes y ordenes de ejemplo.
