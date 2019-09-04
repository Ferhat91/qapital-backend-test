Real-time processing of incoming transactions. 

The project is just a starter, there is much more that can be done/ has not been tested properly. 

- Reusable code should not be duplicated, there should be a common module for this purpose.
- Versions should be soft linked and not hardcoded, there should be a properties file.
- There are no unitTests.
- There are no proper automated IntegrationTest

Flow can be tested by:

1. Start KafkaServerApplication
2. Start SavingsApp
3. Run IntegrationTest in transactions-service (without KafkaApp included)

Check logs for SavingsApp or curl it.