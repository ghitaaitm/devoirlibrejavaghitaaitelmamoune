package com.bank.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public final class Transaction {

    private final String id;
    private final TypeTransaction type;
    private final  Instant timeStamp;
    private final String reference;
    private final Set<Compte> comptesSource;  // Immutable set of source accounts
    private final Set<Compte> comptesDest;    // Immutable set of destination accounts
    private final long clientId;
    private Comparable<String> date;
    private Set<Compte> comptes;        // Immutable set of all accounts involved
    private double amount;// Amount of the transaction
    private double montant ;
    // Constructor with all parameters
    public Transaction(int id, double montant, TypeTransaction type, String date, int clientId) {
        this.id = String.valueOf(id);
        this.montant = montant;
        this.type = type;
        this.date = date;
        this.clientId = clientId;
        timeStamp = null;
        reference = "";
        comptesSource = Set.of();
        comptesDest = Set.of();
    }
    

    // Constructor accepting id, type, amount, timestamp, and a single Compte object
    public Transaction(String id, TypeTransaction type, double amount, String date, Compte clientId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timeStamp = Instant.parse(date);
        this.reference = generateReference();
        this.comptesSource = Collections.singleton(new Compte(clientId.getId()));  // Source account based on clientId
        this.comptesDest = Collections.emptySet();  // No destination accounts
        this.comptes = Collections.unmodifiableSet(new HashSet<>(comptesSource));  // Immutable set of source accounts
        this.date = null;
        this.clientId = 0;
    }

    // Constructor accepting id, amount, type, timestamp, reference, and a single Compte object
    public Transaction(String id, double amount, TypeTransaction type, String date, String reference, int clientId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.timeStamp = Instant.parse(date);
        this.reference = (reference != null) ? reference : generateReference();
        this.comptesSource = Collections.singleton(new Compte(clientId));
        this.comptesDest = Collections.singleton(new Compte(clientId));  // Destination same as source for this constructor
        this.comptes = Collections.unmodifiableSet(new HashSet<>(comptesSource));
        this.date = null;
        this.clientId = 0;
    }

    public Transaction(int id, double montant, TypeTransaction type, String date, int clientId, String id1, Instant timeStamp, String reference, Set<Compte> comptesSource, Set<Compte> comptesDest) {
        this.id = id1;
        this.timeStamp = timeStamp;
        this.reference = reference;
        this.comptesSource = comptesSource;
        this.comptesDest = comptesDest;
        this.montant = montant;
        this.type = type;
        this.date = null;
        this.clientId = 0;
    }


    // Constructor to initialize transaction with source and destination accounts
    public Transaction(TypeTransaction type, Set<Compte> comptesSource, Set<Compte> comptesDest) {
        this.id = String.valueOf(generateUniqueTransactionId());
        this.type = (type != null) ? type : determineTransactionType(comptesSource, comptesDest);
        this.timeStamp = Instant.now();
        this.reference = generateReference();
        this.amount = 0.0;

        this.comptesSource = Collections.unmodifiableSet(new HashSet<>(comptesSource));
        this.comptesDest = Collections.unmodifiableSet(new HashSet<>(comptesDest));

        Set<Compte> allComptes = new HashSet<>(comptesSource);
        allComptes.addAll(comptesDest);
        this.comptes = Collections.unmodifiableSet(allComptes);  // All accounts involved
        date = null;
        clientId = 0;
    }

    public Transaction(String type, String reference, Set<Compte> comptesSource, Set<Compte> comptesDest) {
        this.type = TypeTransaction.valueOf(type);  // Initialisation du type de la transaction
        this.reference = reference;  // Initialisation de la référence de la transaction
        this.comptesSource = comptesSource;  // Initialisation du set de comptes source
        this.comptesDest = comptesDest;  // Initialisation du set de comptes destinataires
        id = "";
        timeStamp = null;
        date = null;
        clientId = 0;
    }

    public String getDetails() {
        return "";
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String transactionDeTest) {
    }



    // Enumeration of transaction types
    public enum TypeTransaction {
        VIRIN,    // Same bank, same country
        VIREST,   // Different banks, same country
        VIRMUTA,  // Same bank, different country
        VIRCHAC   // Different banks, different countries
    }

    // Determine transaction type based on source and destination accounts
    private static TypeTransaction determineTransactionType(Set<Compte> comptesSource, Set<Compte> comptesDest) {
        boolean sameBank = comptesSource.stream()
                .allMatch(cs -> comptesDest.stream()
                        .allMatch(cd -> cs.getClient().getBanque().equals(cd.getClient().getBanque())));
        boolean sameCurrency = comptesSource.stream()
                .allMatch(cs -> comptesDest.stream()
                        .allMatch(cd -> cs.getDevise().equals(cd.getDevise())));

        if (sameBank && sameCurrency) {
            return TypeTransaction.VIRIN;
        } else if (!sameBank && sameCurrency) {
            return TypeTransaction.VIREST;
        } else if (sameBank && !sameCurrency) {
            return TypeTransaction.VIRMUTA;
        } else {
            return TypeTransaction.VIRCHAC;
        }
    }

    // Generate a unique reference for each transaction
    private static String generateReference() {
        return "TX-" + System.currentTimeMillis();
    }

    private static int generateUniqueTransactionId() {
        return Integer.parseInt(String.valueOf((int) (System.currentTimeMillis() % 10000) + (int)(Math.random() * 100)));  // Simple unique ID using time and randomness
    }
}
