package qapital.transactions.service;

import com.google.common.collect.Lists;
import qapital.transactions.domain.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DummyTransactionsGenerator {

  private static final List<String> transactionDescriptions =
    Lists.newArrayList("Starbucks", "McDonald's", "Apple Itunes", "Salary", "Amazon", "Walmart", "Papa Joe's"); // List.of

  private static AtomicLong transactionIdGenerator = new AtomicLong();

  public static List<Transaction> createDummyTransactions(Long userId) {
    List<String> getRandomPurchaseDescriptions = getRandomPurchaseDescriptions();

    List<Transaction> transactions = getRandomPurchaseDescriptions.stream().map(description -> Transaction.builder()
      .withId(incrementAndGetId(transactionIdGenerator))
      .withUserId(userId)
      .withAmount(description.equals("Salary") ? -1 * generateNegativeRandomDouble() : generateNegativeRandomDouble())
      .withPurchaseDescription(description)
//      .withExecutionTime(getRandomLocalDate())
      .build()).collect(Collectors.toList());

    return transactions;
  }

  private static List<String> getRandomPurchaseDescriptions(){
    Long amountOfRandomTransactionsToCreate = new Random(new Random().nextInt()).nextLong();

    List<String> placeDescriptionOfPurchase = Stream.iterate(0, n -> n + 1).limit(amountOfRandomTransactionsToCreate)
      .map(n -> getRandomIntegerInRange(0,transactionDescriptions.size() -1))
      .map(transactionDescriptions::get)
      .collect(Collectors.toList());

    return placeDescriptionOfPurchase;
  }

  private static LocalDate getRandomLocalDate(){
    return LocalDate.of(2019, getRandomIntegerInRange(12,1), getRandomIntegerInRange(1,29));
  }

  private static Long incrementAndGetId(AtomicLong atomicLong){     //FIX FIX FIX
    return new AtomicLong(atomicLong.incrementAndGet()).get();
  }

  private static Double generateNegativeRandomDouble(){
    return -new Random().nextDouble();
  }

  private static Integer getRandomIntegerInRange(Integer min,Integer max) {
    return new Random().nextInt((max - min) + 1) + min;
  }
}
