# automatic-irrigation-system

- Application using h2 database so it does not need any database server in order to start.
- Sample prediction data are inserted into PREDICTION_CONFIG table with crop: rice and area: 200
- Configuring the plot by providing (start date, end date, intervalUnit, timeInterval, amountOfWater)
- Interval unit values (MINUTE, HOUR, DAY, WEEK, MONTH, YEAR).
- Schduled job will be triggered on configuring the plot. (multiple configuration allowed for single plot)
- unit tests are provided.
