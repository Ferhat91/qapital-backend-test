

trait TestDataContext {

    def transaction = [
             id: new Random().nextLong(),
             userId:new Random().nextLong(),
//             executionTime: Timestamp.from(Instant.now()),
             amount: new BigDecimal(new Random().nextLong()),
             purchaseDescription:"KorvMedBrod"
    ]
}