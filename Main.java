import java.util.*;

class User {
    String name;
    String email;
    String password;
    String role; // student or admin

    User(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

class Question {
    int id;
    String subject;
    String text;
    List<String> options;
    int correctIndex;
    String difficulty;

    Question(int id, String subject, String text, List<String> options, int correctIndex, String difficulty) {
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.options = new ArrayList<>(options);
        this.correctIndex = correctIndex;
        this.difficulty = difficulty;
    }

    void display() {
        System.out.println("\nQuestion ID: " + id);
        System.out.println("Subject: " + subject);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Q: " + text);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }
}

class Quiz {
    String quizId;
    String title;
    String subject;
    int durationMinutes;
    List<Question> questions;

    Quiz(String quizId, String title, String subject, int durationMinutes) {
        this.quizId = quizId;
        this.title = title;
        this.subject = subject;
        this.durationMinutes = durationMinutes;
        this.questions = new ArrayList<>();
    }

    void addQuestion(Question q) {
        questions.add(q);
    }
}

class Result {
    String resultId;
    String studentEmail;
    String quizId;
    int score;
    int total;
    String status;
    int timeTakenSeconds;

    Result(String resultId, String studentEmail, String quizId, int score, int total, int timeTakenSeconds) {
        this.resultId = resultId;
        this.studentEmail = studentEmail;
        this.quizId = quizId;
        this.score = score;
        this.total = total;
        this.timeTakenSeconds = timeTakenSeconds;
        this.status = (score * 100 / total) >= 50 ? "Passed" : "Failed";
    }

    void display() {
        System.out.println("\nResult ID: " + resultId);
        System.out.println("Student: " + studentEmail);
        System.out.println("Quiz: " + quizId);
        System.out.println("Score: " + score + "/" + total);
        System.out.println("Status: " + status);
        System.out.println("Time Taken: " + timeTakenSeconds + " sec");
    }
}

class ActivityNode {
    String activity;
    ActivityNode next;

    ActivityNode(String activity) {
        this.activity = activity;
    }
}

class ActivityLinkedList {
    ActivityNode head;

    void add(String activity) {
        ActivityNode node = new ActivityNode(activity);
        if (head == null) {
            head = node;
            return;
        }
        ActivityNode temp = head;
        while (temp.next != null) temp = temp.next;
        temp.next = node;
    }

    void show() {
        if (head == null) {
            System.out.println("No recent activities.");
            return;
        }
        System.out.println("\nRecent Login / Activity History:");
        ActivityNode temp = head;
        while (temp != null) {
            System.out.println("- " + temp.activity);
            temp = temp.next;
        }
    }
}

class QuizSecureSystem {
    HashMap<String, User> users;
    HashMap<String, Quiz> quizzes;
    HashMap<Integer, Question> questionBank;
    ArrayList<Result> results;
    Queue<Result> submissionQueue;
    Stack<String> actionStack;
    ActivityLinkedList activityHistory;
    Scanner sc;
    User currentUser;

    QuizSecureSystem() {
        users = new HashMap<>();
        quizzes = new HashMap<>();
        questionBank = new HashMap<>();
        results = new ArrayList<>();
        submissionQueue = new LinkedList<>();
        actionStack = new Stack<>();
        activityHistory = new ActivityLinkedList();
        sc = new Scanner(System.in);

        seedUsers();
        loadQuestionBank();
        loadQuizzes();
    }

    void seedUsers() {
        users.put("student@quiz.com", new User("Student User", "student@quiz.com", "student123", "student"));
        users.put("admin@quiz.com", new User("Admin User", "admin@quiz.com", "admin123", "admin"));
    }

    void loadQuestionBank() {
        addQuestion(new Question(1, "Java", "Which keyword is used to inherit a class in Java?",
                Arrays.asList("this", "extends", "implements", "super"), 1, "easy"));

        addQuestion(new Question(2, "Java", "Which collection allows dynamic array behavior?",
                Arrays.asList("HashMap", "Queue", "ArrayList", "Stack"), 2, "easy"));

        addQuestion(new Question(3, "Java", "Which method is the entry point of Java program?",
                Arrays.asList("start()", "run()", "main()", "init()"), 2, "easy"));

        addQuestion(new Question(4, "DSA", "Which data structure follows FIFO?",
                Arrays.asList("Stack", "Queue", "Tree", "Graph"), 1, "easy"));

        addQuestion(new Question(5, "DSA", "Which sorting algorithm has average O(n log n)?",
                Arrays.asList("Bubble Sort", "Merge Sort", "Selection Sort", "Insertion Sort"), 1, "medium"));

        addQuestion(new Question(6, "DBMS", "SQL stands for?",
                Arrays.asList("Structured Query Language", "Sequential Query Language", "Simple Query Logic", "System Query Link"), 0, "easy"));

        addQuestion(new Question(7, "Networks", "Which protocol is used for web pages?",
                Arrays.asList("FTP", "HTTP", "SMTP", "TCP"), 1, "easy"));

        addQuestion(new Question(8, "Java", "Which keyword is used for interface implementation?",
                Arrays.asList("extends", "implements", "inherits", "instanceof"), 1, "medium"));
    }

    void addQuestion(Question q) {
        questionBank.put(q.id, q);
    }

    void loadQuizzes() {
        Quiz javaQuiz = new Quiz("QZ101", "Java Programming Fundamentals", "Java", 10);
        javaQuiz.addQuestion(questionBank.get(1));
        javaQuiz.addQuestion(questionBank.get(2));
        javaQuiz.addQuestion(questionBank.get(3));
        javaQuiz.addQuestion(questionBank.get(8));

        Quiz dsaQuiz = new Quiz("QZ102", "Data Structures Basics", "DSA", 8);
        dsaQuiz.addQuestion(questionBank.get(4));
        dsaQuiz.addQuestion(questionBank.get(5));

        Quiz mixedQuiz = new Quiz("QZ103", "Computer Science Mixed Quiz", "CS", 12);
        mixedQuiz.addQuestion(questionBank.get(1));
        mixedQuiz.addQuestion(questionBank.get(4));
        mixedQuiz.addQuestion(questionBank.get(6));
        mixedQuiz.addQuestion(questionBank.get(7));

        quizzes.put(javaQuiz.quizId, javaQuiz);
        quizzes.put(dsaQuiz.quizId, dsaQuiz);
        quizzes.put(mixedQuiz.quizId, mixedQuiz);
    }

    void registerUser() {
        System.out.print("Enter full name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        if (users.containsKey(email)) {
            System.out.println("User already exists.");
            return;
        }

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.print("Enter role (student/admin): ");
        String role = sc.nextLine().toLowerCase();

        users.put(email, new User(name, email, password, role));
        actionStack.push("Registered user: " + email);
        activityHistory.add("Registered: " + email);

        System.out.println("Registration successful.");
    }

    boolean login() {
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User u = users.get(email);
        if (u != null && u.password.equals(password)) {
            currentUser = u;
            actionStack.push("Login: " + email);
            activityHistory.add("Login: " + email);
            System.out.println("Login successful. Welcome, " + u.name + " (" + u.role + ")");
            return true;
        }

        System.out.println("Invalid credentials.");
        return false;
    }

    void showAllQuizzes() {
        System.out.println("\n===== AVAILABLE QUIZZES =====");
        for (Quiz q : quizzes.values()) {
            System.out.println(q.quizId + " | " + q.title + " | Subject: " + q.subject +
                    " | Duration: " + q.durationMinutes + " min | Questions: " + q.questions.size());
        }
    }

    void searchQuizById() {
        System.out.print("Enter quiz ID: ");
        String quizId = sc.nextLine();

        Quiz q = quizzes.get(quizId);
        if (q == null) {
            System.out.println("Quiz not found.");
        } else {
            System.out.println("Quiz Found: " + q.title + " | Subject: " + q.subject);
        }
    }

    void searchQuestionById() {
        System.out.print("Enter question ID: ");
        int id = Integer.parseInt(sc.nextLine());

        Question q = questionBank.get(id);
        if (q == null) {
            System.out.println("Question not found.");
        } else {
            q.display();
        }
    }

    void attemptQuiz() {
        if (currentUser == null || !currentUser.role.equals("student")) {
            System.out.println("Only logged-in students can attempt quizzes.");
            return;
        }

        showAllQuizzes();
        System.out.print("Enter quiz ID to attempt: ");
        String quizId = sc.nextLine();

        Quiz quiz = quizzes.get(quizId);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }

        int score = 0;
        long startTime = System.currentTimeMillis();

        System.out.println("\nStarting Quiz: " + quiz.title);
        System.out.println("Duration: " + quiz.durationMinutes + " minutes");
        System.out.println("Questions will be auto-graded.\n");

        for (Question q : quiz.questions) {
            q.display();
            System.out.print("Enter answer (1-" + q.options.size() + "): ");
            int ans;
            try {
                ans = Integer.parseInt(sc.nextLine()) - 1;
            } catch (Exception e) {
                ans = -1;
            }

            if (ans == q.correctIndex) {
                score++;
            }
        }

        long endTime = System.currentTimeMillis();
        int timeTaken = (int) ((endTime - startTime) / 1000);

        Result result = new Result(
                "RES" + (results.size() + 1),
                currentUser.email,
                quiz.quizId,
                score,
                quiz.questions.size(),
                timeTaken
        );

        results.add(result);
        submissionQueue.offer(result);
        actionStack.push("Attempted quiz: " + quiz.quizId + " by " + currentUser.email);

        System.out.println("\nQuiz submitted successfully.");
        result.display();
    }

    void processNextSubmission() {
        if (currentUser == null || !currentUser.role.equals("admin")) {
            System.out.println("Only admin can process submissions.");
            return;
        }

        Result r = submissionQueue.poll();
        if (r == null) {
            System.out.println("No submissions in queue.");
        } else {
            System.out.println("Processing submission:");
            r.display();
        }
    }

    void showAllResults() {
        if (results.isEmpty()) {
            System.out.println("No results available.");
            return;
        }

        System.out.println("\n===== ALL RESULTS =====");
        for (Result r : results) {
            r.display();
        }
    }

    void sortResultsByScore() {
        if (results.isEmpty()) {
            System.out.println("No results to sort.");
            return;
        }

        ArrayList<Result> sorted = new ArrayList<>(results);
        sorted.sort((a, b) -> Integer.compare(b.score, a.score));

        System.out.println("\n===== RESULTS SORTED BY SCORE =====");
        for (Result r : sorted) {
            System.out.println(r.studentEmail + " | " + r.quizId + " | Score: " + r.score + "/" + r.total + " | " + r.status);
        }
    }

    void showAdminAnalytics() {
        if (results.isEmpty()) {
            System.out.println("No analytics available.");
            return;
        }

        int totalAttempts = results.size();
        int passed = 0;
        int failed = 0;

        for (Result r : results) {
            if (r.status.equals("Passed")) passed++;
            else failed++;
        }

        System.out.println("\n===== ADMIN ANALYTICS =====");
        System.out.println("Total Attempts: " + totalAttempts);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.printf("Pass Percentage: %.2f%%%n", (passed * 100.0 / totalAttempts));
    }

    void undoLastAction() {
        if (actionStack.isEmpty()) {
            System.out.println("No recent actions.");
        } else {
            System.out.println("Undo action: " + actionStack.pop());
        }
    }

    void showRecentActivities() {
        activityHistory.show();
    }

    void showMenu() {
        while (true) {
            System.out.println("\n===== QUIZSECURE JAVA DSA MENU =====");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Show All Quizzes");
            System.out.println("4. Search Quiz by ID (HashMap)");
            System.out.println("5. Search Question by ID (HashMap)");
            System.out.println("6. Attempt Quiz");
            System.out.println("7. Process Next Submission (Queue)");
            System.out.println("8. Show All Results");
            System.out.println("9. Sort Results by Score");
            System.out.println("10. Show Admin Analytics");
            System.out.println("11. Undo Last Action (Stack)");
            System.out.println("12. Show Recent Activities (LinkedList)");
            System.out.println("13. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    showAllQuizzes();
                    break;
                case "4":
                    searchQuizById();
                    break;
                case "5":
                    searchQuestionById();
                    break;
                case "6":
                    attemptQuiz();
                    break;
                case "7":
                    processNextSubmission();
                    break;
                case "8":
                    showAllResults();
                    break;
                case "9":
                    sortResultsByScore();
                    break;
                case "10":
                    showAdminAnalytics();
                    break;
                case "11":
                    undoLastAction();
                    break;
                case "12":
                    showRecentActivities();
                    break;
                case "13":
                    System.out.println("Exiting QuizSecure...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        QuizSecureSystem system = new QuizSecureSystem();
        system.showMenu();
    }
}