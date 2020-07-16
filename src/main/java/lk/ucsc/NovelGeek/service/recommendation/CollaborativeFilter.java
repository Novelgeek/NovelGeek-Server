package lk.ucsc.NovelGeek.service.recommendation;

import lk.ucsc.NovelGeek.model.book.Books;
import lk.ucsc.NovelGeek.model.Users;
import lk.ucsc.NovelGeek.repository.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class CollaborativeFilter {

    @Autowired
    PreProcessData preProcessDataService;

    @Autowired
    BookRepository bookRepository;


    private  Map<Book, Map<Book, Double>> diff = new HashMap<>();
    private  Map<Book, Map<Book, Integer>> freq = new HashMap<>();
    private  Map<User, HashMap<Book, Double>> inputData;
    private  Map<User, HashMap<Book, Double>> outputData = new HashMap<>();
    private  List<Books> returnData = new ArrayList<Books>();
    private boolean isCalulated = false;

    private Users users;

    public List<Books> slopeOne(Users user) {
        outputData.clear();
        diff.clear();
        freq.clear();
        returnData.clear();

        users = user;
        isCalulated = true;

        inputData = preProcessDataService.initializeData();
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        predict(inputData);
        getRecommendations();
        printData(outputData);
        getFriends();
        return returnData;
    }

    public List<Books> getRecommendations (){
        returnData.clear();
        for (User user : outputData.keySet()) {
            if (user.getUsername().equals(users.getEmail())){
                for (Book j : outputData.get(user).keySet()) {
                    if (outputData.get(user).get(j) > 3){
                        returnData.add(bookRepository.findByTitle(j.getItemName()));
                    }
                }
            }
        }

        return returnData;
    }

    public void getFriends() {
        HashMap<Book, Double> currentUser = new HashMap<>();
        for (User user : outputData.keySet()) {
            if (user.getUsername().equals(users.getEmail())){
                for (Book j : outputData.get(user).keySet()) {
                    currentUser.put(j, outputData.get(user).get(j));
                }

            }
        }
        for (User user : outputData.keySet()) {
            double sum =0;

            if (!user.getUsername().equals(users.getEmail())){
                for (Book j : outputData.get(user).keySet()) {
                    sum = sum + (currentUser.get(j) - outputData.get(user).get(j));
                }
                System.out.println(sum);
            }
        }

    }

    public List<Books> getMyRecommendations (Users currentUser){
        if (isCalulated){
            return getRecommendations();
        } else {

            return slopeOne(currentUser);
        }

    }


    private void buildDifferencesMatrix(Map<User, HashMap<Book, Double>> data) {
        for (HashMap<Book, Double> user : data.values()) {
            for (Entry<Book, Double> e : user.entrySet()) {
                // adding an entry if the item doesn't exist.
                if (!diff.containsKey(e.getKey())) {
                    diff.put(e.getKey(), new HashMap<Book, Double>());
                    freq.put(e.getKey(), new HashMap<Book, Integer>());
                }

                // e.getKey is item
                for (Entry<Book, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = freq.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (diff.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = diff.get(e.getKey()).get(e2.getKey()).doubleValue();
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    freq.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    diff.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Book j : diff.keySet()) {
            for (Book i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i).doubleValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }


    private void predict(Map<User, HashMap<Book, Double>> data) {
        HashMap<Book, Double> uPred = new HashMap<Book, Double>();
        HashMap<Book, Integer> uFreq = new HashMap<Book, Integer>();
        for (Book j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<User, HashMap<Book, Double>> e : data.entrySet()) {
            for (Book j : e.getValue().keySet()) {
                for (Book k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Book, Double> clean = new HashMap<Book, Double>();
            for (Book j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    if (uPred.get(j).doubleValue() / uFreq.get(j).intValue() > 5){
                        clean.put(j, 5.0);
                    } else {
                        clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                    }

                }
            }
            for (Book j : PreProcessData.books) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else if (!clean.containsKey(j)) {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
        printMatrix(outputData);
    }

    private void printData(Map<User, HashMap<Book, Double>> data) {
        for (User user : data.keySet()) {
//            if (user.getUsername().equals(users.getEmail())){
//                System.out.println(user.getUsername() + ":");
//                print(data.get(user));
//            }
            System.out.println(user.getUsername() + ":");
            print(data.get(user));
        }
    }

    private void print(HashMap<Book, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Book j : hashMap.keySet()) {
            System.out.println(" " + j.getItemName() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

    private void printMatrix(Map<User, HashMap<Book, Double>> data) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for( User user: data.keySet()){
            System.out.print(user.getUsername()+ "  ");

            for (Book book: data.get(user).keySet()){
                System.out.print(formatter.format(data.get(user).get(book).doubleValue())+ " ");
            }
            System.out.println();
        }
    }

}