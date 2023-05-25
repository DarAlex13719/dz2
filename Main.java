import java.util.*;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                String texts = generateRoute("RLRFR", 100);
                int colvoR = (int) texts.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(colvoR)) {
                        sizeToFreq.put(colvoR, sizeToFreq.get(colvoR) + 1);
                    } else {
                        sizeToFreq.put(colvoR, 1);
                    }
                }
            });

            threads.add(thread);
            thread.start();

        }

        for (Thread thread : threads) {
            thread.join();
        }

        Map.Entry<Integer, Integer> max = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get();

        System.out.println("Самое частое количество повторений (R в маршруте): " + max.getKey() + " (встретилось " + max.getValue() + " раз)");
        System.out.println();

        System.out.println("Другие размеры: ");
        sizeToFreq.entrySet().forEach(e -> System.out.println(" - " + e.getKey() + " (" + e.getValue() + " раз)"));

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}