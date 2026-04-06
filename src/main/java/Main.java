import CollectionsAndGenerics.Practice1.MyArrayList;
import CollectionsAndGenerics.Practice2.WordAnalizer;
import CollectionsAndGenerics.Practice3.LRUCache;
import CollectionsAndGenerics.Practice3A.Contact;
import CollectionsAndGenerics.Practice3A.RecentContacts;
import CollectionsAndGenerics.Practice3B.GradeTracker;
import CollectionsAndGenerics.Practice4.Graph;
import CollectionsAndGenerics.Practice4A.SocialNetwork;
import CollectionsAndGenerics.Practice4B.CityRoutes;
import CollectionsAndGenerics.Practice5.Inventory;
import CollectionsAndGenerics.Practice5.Product;
import HilosYConcurrencia.Practice1.AtomicCounter;
import HilosYConcurrencia.Practice1.SynchronizedCounter;
import HilosYConcurrencia.Practice1.UnsafeCounter;
import HilosYConcurrencia.Practice2.SharedBuffer;
import LambdasAndStreams.Practice2.Order;
import LambdasAndStreams.Practice3.Address;
import LambdasAndStreams.Practice3.User;
import LambdasAndStreams.Practice3.UserService;
import LambdasAndStreams.Practice4.Combiner;
import LambdasAndStreams.Practice4.Pipeline;
import LambdasAndStreams.Practice4.Validator;
import LambdasAndStreams.Practice5.Student;
import ManejoExcepciones.Practice1.*;
import ManejoExcepciones.Practice2.*;
import ManejoExcepciones.Practice3.Bank;
import Practica2.*;
import Practica3.*;
import Practica4.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        practice4HilosYConcurrencia();
    }

    public static void practice4HilosYConcurrencia () {
        List<CompletableFuture<Void>> pipelines = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            int id = i;
            CompletableFuture<Void> pipeline = CompletableFuture.supplyAsync(() -> {
                        if( id == 3) throw new RuntimeException("User 3 not found");
                        return fetchUser(id);
                    })
                    .thenApplyAsync(Main::fetchEmail)
                    .thenAcceptAsync(email -> System.out.println(sendWelcome(email)))
                    .exceptionally(ex -> {
                        System.out.println("Error: " + ex.getCause().getMessage());
                        return null;
                    });
            pipelines.add(pipeline);
        }
        CompletableFuture.allOf(pipelines.toArray(new CompletableFuture[0])).join();
        System.out.println("Todos los email enviados");
    }

    public static String fetchUser (int id) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "User_" + id;
    }

    public static String fetchEmail (String user) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return user + "@company.com";
    }

    public static String sendWelcome (String email) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Welcome sent to: " + email;
    }

    public static void practice3HilosYConcurrencia() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        /*for (int i = 1; i <= 10; i++) {
            int finalI = i;
            Runnable tarea =  () -> {
                try {
                    System.out.println("Procesando request: " + finalI + " en hilo: " + Thread.currentThread().getName());
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500,1501));
                    System.out.println("Request " + finalI + " Completada");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };
            executorService.submit(tarea);
        }*/
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int id = i;
            Future<String> future = executorService.submit( () -> {
                System.out.println("Procesando request: " + id + " en hilo: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500,1501));
                    return "Resultado del request #" + id +
                            " (hilo: " + Thread.currentThread().getName() + ")";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            });
            futures.add(future);
        }

        executorService.shutdown();
        executorService.awaitTermination(30,TimeUnit.SECONDS);
        // Recolectá resultados en orden
        for (Future<String> f : futures) {
            System.out.println(f.get()); // ya terminaron → no bloquea en la práctica
        }
    }

    public static void practice2HilosYConcurrencia() throws InterruptedException {
        SharedBuffer sharedBuffer = new SharedBuffer(5);
        Runnable tareaProducer = () -> {
            for (int i = 0; i < 20; i++) {
                try {
                    sharedBuffer.produce(i);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable tareaConsumer = () -> {
            for (int i = 0; i < 20; i++) {
                try {
                    sharedBuffer.consume();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        Runnable t2Producer = () -> {
            for (int i = 0; i < 20; i++) {
                try {
                    queue.put(i);
                    System.out.println("Produced: " + i + " | Size: " + queue.size());
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable t2Consumer = () -> {
            for (int i = 0; i < 20; i++) {
                try {
                    int value = queue.take();
                    System.out.println("Consumed: " + value + " | Size: " + queue.size());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        System.out.println("Ejecucion Parte A");

        Thread tProducer = new Thread(tareaProducer);
        Thread tConsumer = new Thread(tareaConsumer);
        tProducer.start();
        tConsumer.start();
        tProducer.join();
        tConsumer.join();
        System.out.println("--------------------------");
        System.out.println("Ejecucion Parte B");
        Thread tProducer2 = new Thread(t2Producer);
        Thread tConsumer2 = new Thread(t2Consumer);
        tProducer2.start();
        tConsumer2.start();
        tProducer2.join();
        tConsumer2.join();
    }

    public static void practice1HilosYConcurrencia () throws InterruptedException {
        AtomicCounter atomicCounter = new AtomicCounter();
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        UnsafeCounter  unsafeCounter = new UnsafeCounter();
        List<Thread>  threads = new ArrayList<>();

        Runnable tarea = () -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.increment();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t =  new Thread(tarea);
            t.start();
            threads.add(t);
        }


        for (Thread t : threads) {
            t.join(); // espera que termine antes de seguir
        }
        System.out.println("Atomic Counter");
        System.out.println(atomicCounter.getCounter());

        System.out.println("Se limpia la lista");

        threads.clear();

        Runnable tarea2 = () -> {
            for (int i = 0; i < 1000; i++) {
                synchronizedCounter.increment();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t =  new Thread(tarea2);
            t.start();
            threads.add(t);
        }


        for (Thread t : threads) {
            t.join(); // espera que termine antes de seguir
        }
        System.out.println("Synchronized Counter");
        System.out.println(synchronizedCounter.getCounter());

        System.out.println("Se limpia la lista");
        threads.clear();

        Runnable tarea3 = () -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t =  new Thread(tarea3);
            t.start();
            threads.add(t);
        }


        for (Thread t : threads) {
            t.join(); // espera que termine antes de seguir
        }
        System.out.println("Unsafe Counter");
        System.out.println(unsafeCounter.getCounter());
    }

    public static void practice5LambdasAndStreams() {
        List<Student> students = Arrays.asList(
                new Student("Ana",     "Engineering", 4.5, 22),
                new Student("Carlos",  "Medicine",    3.8, 25),
                new Student("María",   "Engineering", 4.5, 20),
                new Student("José",    "Law",         4.1, 23),
                new Student("Laura",   "Medicine",    4.5, 22),
                new Student("Pedro",   "Engineering", 3.9, 21),
                new Student("Isabel",  "Law",         4.1, 24),
                new Student("Miguel",  "Medicine",    3.7, 26)
        );
        System.out.println("Por GPA descendente");
        System.out.println(students.stream().sorted(Comparator.comparing(Student::getGpa).reversed()).toList());

        System.out.println("Por carrera alfabeticamente, luego por GPA descendente");
        System.out.println(students.stream().sorted(Comparator.comparing(Student::getCareer).thenComparing(Comparator.comparing(Student::getGpa).reversed())).toList());

        System.out.println("Por carrera alfabética, luego por GPA descendente, \n" +
                "    luego por nombre alfabético (desempate final)");
        System.out.println(students.stream()
                .sorted(Comparator.comparing(Student::getCareer).thenComparing(Comparator.comparing(Student::getGpa).reversed()).thenComparing(Student::getName))
                .toList());

        System.out.println("Por edad ascendente, luego por nombre descendente");
        System.out.println(students.stream().sorted(
                Comparator.comparing(Student::getAge)
                        .thenComparing(Comparator.comparing(Student::getName).reversed())
            ).toList()
        );

        System.out.println("El estudiante con mejor GPA de cada carrera");
        System.out.println(students.stream().collect(Collectors.groupingBy(Student::getCareer, Collectors.maxBy(Comparator.comparing(Student::getGpa)))));
    }

    public static void practice4LambdasAndStreams() {
//        Pipeline<String> pipelineStr = new Pipeline<>(" hola mundo ").map(String::trim).map(String::toUpperCase)
//                .map(str -> str.replaceAll("\\s", "_")).map(str -> {
//                    System.out.println(str);
//                    return str;
//                });
        String resultado = new Pipeline<>(" hola mundo ")
                .map(String::trim)
                .map(String::toUpperCase)
                .map(str -> str.replaceAll("\\s", "_"))
                .get();
        System.out.println(resultado);
        try {
            Pipeline<Integer> pipelineInteger = new Pipeline<>(-5).validate((entero) -> entero >= 0,"Valor negativo")
                    .map(i -> i * 10);
            Validator<Integer> positiveValidator = value -> value >= 0;
            new Pipeline<>(-5).validate(positiveValidator, "Valor negativo");
        } catch (IllegalArgumentException e) {
            System.out.println(
                    e.getMessage()
            );
        }

        Combiner<String, String, String> combiner = (nombre, apellido) -> apellido.toUpperCase() +", "+ nombre;
        String res = combiner.combine("Juan","Garia");
        System.out.println(res);
    }

    public static void practice3LambdasAndStreams() {
        Map<Integer, User>  users = new HashMap<>();
        users.put(1,new User(1,"Maria","maria@email.com",new Address("Armenia","Colombia")));
        users.put(2,new User(2,"Jose","jose@mail.com",null));
        users.put(3,new User(3,"Juan","",new Address("Manizales","Colombia")));
        UserService userService = new UserService(users);

        System.out.println("Get usuario con info completa:");
        userService.findById(1).ifPresent(System.out::println);
        System.out.println();

        System.out.println("Get usuario no existente");
        userService.findById(70).ifPresentOrElse(System.out::println,() -> System.out.println("User not found"));
        System.out.println();

        System.out.println("Get ciudad de usuario con address null");
        System.out.println(userService.getCity(2));
        System.out.println();

        System.out.println("Get dominio de email");
        userService.getEmailDomain(1).ifPresent(System.out::println);
        System.out.println();

        System.out.println("getCity de usuario existente");
        userService.getCity(3).ifPresent(System.out::println);
        System.out.println();

        System.out.println("Get displayName de usaurio existente");
        System.out.println(userService.getDisplayName(3));
        System.out.println();

        System.out.println("Get displayName de id inexistente");
        System.out.println(userService.getDisplayName(5));
    }

    public static void practice2LambdasAndStreams() {
        List<Order> orders = Arrays.asList(
                new Order("001", "Ana", "Laptop", 1, 1200.0, "PENDING"),
                new Order("002", "Carlos", "Mouse", 3, 25.0, "SHIPPED"),
                new Order("003", "María", "Laptop", 2, 1200.0, "CANCELLED"),
                new Order("004", "Ana", "Keyboard", 1, 75.0, "PENDING"),
                new Order("005", "José", "Monitor", 1, 350.0, "SHIPPED"),
                new Order("006", "Laura", "Mouse", 5, 25.0, "PENDING"),
                new Order("007", "Carlos", "Laptop", 1, 1200.0, "PENDING"),
                new Order("008", "María", "Headset", 2, 89.0, "SHIPPED"),
                new Order("009", "José", "Keyboard", 3, 75.0, "CANCELLED"),
                new Order("010", "Ana", "Monitor", 2, 350.0, "PENDING"),
                new Order("011", "Ana", "CPU", 5, 245, "CANCELLED"),
                new Order("012", "María", "CPU", 5, 245, "CANCELLED")
        );

        System.out.println("Total de ingresos de pedidos SHIPPED");
        System.out.println(orders.stream().filter(o -> o.getStatus().equalsIgnoreCase("shipped")).mapToDouble(o -> o.getQuantity() * o.getUnitPrice()).sum());

        System.out.println("Pedidos PENDING ordenados por total descendente");
        System.out.println(orders.stream().filter(o -> o.getStatus().equalsIgnoreCase("pending")).sorted(Comparator.comparing(Order::getTotal).reversed()).toList());

        System.out.println("Clientes que tienen al menos un pedido CANCELLED");
        System.out.println(orders.stream().filter(o -> o.getStatus().equalsIgnoreCase("cancelled")).map(o -> o.getCustomer()).collect(Collectors.toSet()));

        System.out.println("Monto total agrupado por status");
        System.out.println(orders.stream().collect(Collectors.groupingBy(Order::getStatus, Collectors.summingDouble(o -> o.getQuantity() * o.getUnitPrice()))));

        System.out.println("¿Todos los pedidos de Ana son PENDING o SHIPPED?");
        boolean flag = orders.stream().filter(o -> o.getCustomer().equalsIgnoreCase("ana")).allMatch(o -> o.getStatus().equalsIgnoreCase("shipped") || o.getStatus().equalsIgnoreCase("pending"));
        System.out.println(flag);

        System.out.println("Pedido con mayor total (sin importar status)");
        orders.stream().max(Comparator.comparing(o -> o.getUnitPrice() * o.getQuantity())).ifPresent(System.out::println);

        System.out.println("Resumen por cliente: nombre → cantidad de pedidos");
        System.out.println(orders.stream().collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting())));

        System.out.println("Lista de productos únicos pedidos en estado PENDING, ordenados");
        System.out.println(orders.stream().filter(o -> o.getStatus().equalsIgnoreCase("pending")).map(Order::getProduct).distinct().sorted().toList());
    }

    public static void practice1LambdasAndStreams() {
        List<LambdasAndStreams.Practice1.Employee> employees = Arrays.asList(
                new LambdasAndStreams.Practice1.Employee("Ana García",     "Engineering", 85000, 32, true),
                new LambdasAndStreams.Practice1.Employee("Carlos López",   "Marketing",   62000, 28, true),
                new LambdasAndStreams.Practice1.Employee("María Rodríguez","Engineering", 92000, 35, false),
                new LambdasAndStreams.Practice1.Employee("José Martínez",  "HR",          48000, 41, true),
                new LambdasAndStreams.Practice1.Employee("Laura Sánchez",  "Engineering", 78000, 29, true),
                new LambdasAndStreams.Practice1.Employee("Pedro Jiménez",  "Marketing",   71000, 33, true),
                new LambdasAndStreams.Practice1.Employee("Isabel Fernández","HR",         52000, 26, true),
                new LambdasAndStreams.Practice1.Employee("Miguel Torres",  "Engineering", 95000, 45, true),
                new LambdasAndStreams.Practice1.Employee("Carmen Díaz",    "Marketing",   68000, 31, false),
                new LambdasAndStreams.Practice1.Employee("Antonio Ruiz",   "Engineering", 88000, 38, true)
        );

        System.out.println("Nombres de empleados activos, ordenados alfabéticamente");
        employees.stream().filter(LambdasAndStreams.Practice1.Employee::isActive).sorted(Comparator.comparing(LambdasAndStreams.Practice1.Employee::getName)).forEach(System.out::println);
        System.out.println();

        System.out.println("Salario promedio del departamento Engineering");
        employees.stream().filter(e -> e.getDepartment().equalsIgnoreCase("Engineering")).mapToDouble(LambdasAndStreams.Practice1.Employee::getSalary).average().ifPresent(System.out::println);

        System.out.println("El empleado con mayor salario (activo o no)");
        System.out.println(employees.stream().reduce((s1,s2) -> s1.getSalary() > s2.getSalary() ? s1 : s2));
        employees.stream().max(Comparator.comparing(LambdasAndStreams.Practice1.Employee::getSalary)).ifPresent(System.out::println);

        System.out.println("Lista de departamentos sin repetidos, ordenados");
        System.out.println(employees.stream().map(LambdasAndStreams.Practice1.Employee::getDepartment).distinct().sorted().toList());

        System.out.println("Existe algun empleado de HR mayor a 40 años?");
        boolean exist = employees.stream().anyMatch(e -> e.getDepartment().equalsIgnoreCase("HR") && e.getAge() > 40);
        System.out.println(exist);

        System.out.println("Suma total del salario de empleados activos");
        System.out.println(employees.stream().filter(LambdasAndStreams.Practice1.Employee::isActive).mapToDouble(LambdasAndStreams.Practice1.Employee::getSalary).sum());

        System.out.println("Empleados agrupados por departamento");
        System.out.println(employees.stream().collect(Collectors.groupingBy(LambdasAndStreams.Practice1.Employee::getDepartment)));

        System.out.println("Top 3 salarios más altos de empleados activos");
        System.out.println(employees.stream().filter(LambdasAndStreams.Practice1.Employee::isActive).sorted(Comparator.comparing(LambdasAndStreams.Practice1.Employee::getSalary).reversed()).limit(3).toList());

        System.out.println("Nombres de empleados de Engineering con salario > 80000," +
                "    en mayúsculas, separados por coma");
        String nameList = employees.stream().filter(e ->  e.getDepartment().equalsIgnoreCase("Engineering") && e.getSalary() > 80000)
                .map(e -> e.getName().toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(nameList);

        System.out.println("Departamento con mayor masa salarial (suma de salarios)" +
                "     entre empleados activos");
        Optional<Map.Entry<String, List<LambdasAndStreams.Practice1.Employee>>> e1 = employees.stream().filter(LambdasAndStreams.Practice1.Employee::isActive).collect(Collectors.groupingBy(LambdasAndStreams.Practice1.Employee::getDepartment))
                .entrySet().stream().max(Comparator.comparing(e -> e.getValue().stream().mapToDouble(LambdasAndStreams.Practice1.Employee::getSalary).sum()));
        System.out.println(e1.get());
    }

    public static void bankTransfer () {
        BankAccount A = new BankAccount("A","053000",1000D);
        BankAccount B = new BankAccount("B","053000",500D);
        BankAccount C = new BankAccount("C","053000",200D);
        C.freeze();

        Bank bank = new Bank();
        bank.addAccount("1",A);
        bank.addAccount("2",B);
        bank.addAccount("3",C);

        System.out.println("Transfer A -> B");
        bank.transfer("1","2",300D);

        System.out.println("Transfer B -> A : 1000");
        try {
            bank.transfer("2","1",1000D);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Transfer A -> C");
        try {
            bank.transfer("1","3",100D);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(A.getSummary());

        System.out.println("Transfer X -> B");
        try {
            bank.transfer("4","2",500D);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void bankAccount() {
        BankAccount bankAccount = new BankAccount("Santiago","053000",1000D);

        System.out.println("Depositando 500");
        bankAccount.deposit(500);
        System.out.println(bankAccount.getSummary());

        System.out.println("Retirando 200");
        bankAccount.withdraw(200);
        System.out.println(bankAccount.getSummary());

        System.out.println("Intentando retirar 2000");
        try {
            bankAccount.withdraw(2000);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Intentado depositar -100");
        try {
            bankAccount.deposit(-100);
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Congelando cuenta");
        bankAccount.freeze();

        System.out.println("Intentando depositar mientras la cuenta esta congelada");
        try {
            bankAccount.deposit(100);
        } catch (AccountFrozenException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Intentando retirar mientras la cuenta esta congelada");
        try {
            bankAccount.withdraw(100);
        } catch (AccountFrozenException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Descongelando cuenta");
        bankAccount.unfreeze();

        System.out.println("Retirar una vez descongelada la cuenta");
        bankAccount.withdraw(100);
        System.out.println(bankAccount.getSummary());

        System.out.println("Capturando excepcion con clase padre");
        try {
            bankAccount.withdraw(-100);
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void manejoExcepciones() {
        UserForm userForm = FormValidator.validateForm("santimarchena", "mar@mail.com", 22, "password1");
        System.out.println(userForm);

        try {
            FormValidator.validateForm("","mar@mail.com",22,"password1");
        } catch (EmptyFieldException e) {
            System.out.println(e.getMessage());
        }

        try {
            FormValidator.validateForm("santi","marmailcom",22,"password1");
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        }

        try {
            FormValidator.validateForm("santi","mar@mail.com",16,"password1");
        } catch (OutOfRangeException e) {
            System.out.println(e.getMessage());
        }

        try {
            FormValidator.validateForm("santi","mar@mail.com",22,"passwor");
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        }

        try {
            FormValidator.validateForm("santi","mar@mail.com",16,"passwor");
        } catch (ValidationExcepton e) {
            System.out.println(e.getMessage());
        }

    }

    public static void inventory() {
        List<Product> products = Arrays.asList(
                new Product("ATK A9 Plus","Perifericos", 140_000D,5,true),
                new Product("Ryzen 5 5600","Componentes", 500_000D,4,true),
                new Product("Cable USB C","Accesorios", 60_000D,10,true),
                new Product("Astro A10","Perifericos", 200_000D,11,true),
                new Product("RTX 3060 12GB","Componentes", 1_900_000D,3,true),
                new Product("Base para audifonos","Accesorios", 50_000D,15,true),
                new Product("Teclado RK R75","Perifericos", 180_000D,7,true),
                new Product("RAM Corsair Vengeance 32 GB DDR4","Componentes", 600_000D,2,true),
                new Product("Antena WIFI USB","Accesorios", 90_000D,5,true)
        );
        Inventory inventory = new Inventory(products);

        System.out.println("Buscar por categoria:");
        System.out.println(inventory.findByCategory("Componentes"));
        System.out.println();

        System.out.println("Buscar por rango de precio");
        System.out.println(inventory.findByPriceRange(50_000D,100_000D));
        System.out.println();

        System.out.println("Producto mas barato en stock:");
        System.out.println(inventory.findCheapest());
        System.out.println();

        System.out.println("Agrupar por categoria:");
        System.out.println(inventory.groupByCategory());
        System.out.println();

        System.out.println("Promedio de precio por categoria:");
        System.out.println(inventory.averagePriceByCategory());
        System.out.println();

        System.out.println("Productos con stock por debajo de 5:");
        System.out.println(inventory.getLowStock(5));
        System.out.println();

        System.out.println("Valor total del inventario:");
        System.out.println(inventory.getTotalValue());
        System.out.println();

        System.out.println("Ordenado por precio ascendente:");
        System.out.println(inventory.getSortedBy(Comparator.comparing(Product::getPrice)));
        System.out.println();

        System.out.println("Ordenado por nombre:");
        System.out.println(inventory.getSortedBy(Comparator.comparing(Product::getName)));
        System.out.println();

        System.out.println("Actualizando stock a 0");
        String find = "Base para audifonos";
        inventory.updateStock(find,0);
        System.out.println(inventory.getProducts().stream().filter(p ->  p.getName().equalsIgnoreCase(find)).findFirst().orElseThrow().isInStock());
        System.out.println();

        System.out.println("Producto mas barato en stock:");
        System.out.println(inventory.findCheapest());
    }

    public static void graphSimulation() {
        Graph<String> graph = new Graph<>();
        graph.addEdge("A","B");
        graph.addEdge("A","C");
        graph.addEdge("B","D");
        graph.addEdge("B","E");
        graph.addEdge("C","D");
        graph.addEdge("D","F");

        System.out.println("bfs");
        System.out.println(graph.bfs("A"));

        System.out.println("dfs");
        System.out.println(graph.dfs("A"));

        System.out.println(graph.hasPath("A","F"));
        System.out.println(graph.hasPath("A","Z"));

        System.out.println("Vecinos de B:");
        System.out.println(graph.getNeighbors("B"));

        Graph<Integer> graphInteger = new Graph<>();
        graphInteger.addEdge(1,2);
        graphInteger.addEdge(2,3);
        System.out.println(graphInteger.hasPath(1,3));

    }

    public static void cityRoutes () {
        CityRoutes ct = new CityRoutes();
        ct.addRoute("Bogotá","Medellin");
        ct.addRoute("Medellin","Cartagena");
        ct.addRoute("Bogotá","Cali");
        ct.addRoute("Cali","Barranquilla");
        ct.addRoute("Barranquilla","Cartagena");
        ct.addRoute("Cali","Pasto");

        System.out.println("Puede viajar de Pasto a Cartagena?:");
        System.out.println(ct.canTravel("Pasto","Cartagena"));

        System.out.println("Puede viajar de Pasto a Lima?:");
        System.out.println(ct.canTravel("Pasto","Lima"));

        System.out.println("Hallar ruta de Pasto a Cartagena:");
        System.out.println(ct.findRoute("Pasto","Cartagena"));
    }

    public static void socialNetwork(){
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.addPerson("Ana");
        socialNetwork.addPerson("Luis");
        socialNetwork.addPerson("Carlos");
        socialNetwork.addPerson("Maria");
        socialNetwork.addPerson("Pedro");

        socialNetwork.addFriendship("Ana","Luis");
        socialNetwork.addFriendship("Ana","Carlos");
        socialNetwork.addFriendship("Luis","Maria");
        socialNetwork.addFriendship("Carlos","Pedro");
        socialNetwork.addFriendship("Maria","Pedro");

        System.out.println("Amigos de Ana");
        System.out.println(socialNetwork.getFriends("Ana"));

        System.out.println("Amigos de amigos de Ana");
        System.out.println(socialNetwork.getFriendsOfFriends("Ana"));

        System.out.println("Persona mas popular");
        System.out.println(socialNetwork.mostPopular());

        System.out.println("Total conexiones");
        System.out.println(socialNetwork.countConnections());
    }

    public static void lruCache(){
        LRUCache<String,Integer> lruCache = new LRUCache<>(3);
        lruCache.put("A",1);
        lruCache.put("B",2);
        lruCache.put("C",3);
        System.out.println(lruCache);
        lruCache.get("A");
        System.out.println(lruCache);
        lruCache.put("D",4);
        System.out.println(lruCache);
        System.out.println(lruCache.get("B"));
    }

    public static void studentGrades() {
        GradeTracker  gt = new GradeTracker();
        gt.addGrade("Carlitos", 3.4);
        gt.addGrade("Carlitos", 3.7);
        gt.addGrade("Carlitos", 4.0);
        gt.addGrade("Daniel", 3.3);
        gt.addGrade("Daniel", 3.3);
        gt.addGrade("Daniel", 3.3);

        System.out.println(gt);

        System.out.println("Promedio de estudiante");
        System.out.println(gt.getAverage("Daniel"));

        System.out.println("Estudiante mejor promedio");
        System.out.println(gt.getBestStudent());

        System.out.println("Obtener todos los promedios");
        System.out.println(gt.getAllAverages());

        System.out.println("Remove Student");
        gt.removeStudent("Daniel");
        System.out.println(gt);
    }

    public static void recentContacts() {
        RecentContacts recentContacts = new RecentContacts(3);
        recentContacts.addContact(new Contact("C1","12334","c1mail.com"));
        recentContacts.addContact(new Contact("C2","12335","c2mail.com"));
        recentContacts.addContact(new Contact("C3","12336","c3mail.com"));
        recentContacts.addContact(new Contact("C4","12337","c4mail.com"));

        System.out.println("Imprimiendo contactos");
        System.out.println(recentContacts.getAll());

        recentContacts.addContact(new Contact("C2","12335","c2mail.com"));

        System.out.println("Imprimiendo contactos");
        System.out.println(recentContacts.getAll());

        System.out.println("Buscando contacto existente");
        System.out.println(recentContacts.getByPhone("12337"));
    }

    public static void wordAnalyzer(){
        String word = "La compañía Sun desarrolló la implementación de referencia original para los compiladores de Java, máquinas virtuales y librerías de clases en 1991, y las publicó por primera vez en 1995. A partir de mayo de 2007, en cumplimiento de las especificaciones del Proceso de la Comunidad Java, Sun volvió a licenciar la mayoría de sus tecnologías de Java bajo la Licencia Pública General de GNU. Otros han desarrollado también implementaciones alternas a estas tecnologías de Sun, tales como el Compilador de Java de GNU y el GNU Classpath.";
        WordAnalizer wordAnalizer = new WordAnalizer(word);
        System.out.println("Procesar texto");
        System.out.println(wordAnalizer.processWords());

        System.out.println("Contar frecuencia de palabras");
        System.out.println(wordAnalizer.countFrequency());

        System.out.println("topN con mas frecuencia");
        System.out.println(wordAnalizer.topN(3));

        System.out.println("Unique Words");
        System.out.println(wordAnalizer.uniqueWords());

        System.out.println("Grouped frequency");
        System.out.println(wordAnalizer.groupByFrequency());
    }


    public static void arrayListPractice () {
        MyArrayList<String> myArrayList1 = new MyArrayList<>();
        MyArrayList<Integer> myArrayList2 = new MyArrayList<>();

        for (int i = 0; i <= 12; i++) {
            String cadena = "cadena";
            myArrayList1.add(cadena + i);
            myArrayList2.add(i);
        }
        System.out.println("Probando Get String: ");
        System.out.println(myArrayList1.get(1));

        System.out.println("Probando Get Integer: ");
        System.out.println(myArrayList2.get(1));

        System.out.println("Probando remove String: ");
        System.out.println(myArrayList1.remove(3));

        System.out.println("Probando remove Integer: ");
        System.out.println(myArrayList2.remove(3));

        System.out.println("Probando contains String: ");
        System.out.println(myArrayList1.contains("cadena1"));

        System.out.println("Probando contains Integer: ");
        System.out.println(myArrayList2.contains(11));

        System.out.println("Probando indexOf String: ");
        System.out.println(myArrayList1.indexOf("cadena1"));

        System.out.println("Probando indexOf Integer: ");
        System.out.println(myArrayList2.indexOf(11));

        System.out.println(myArrayList1.toString());
        System.out.println(myArrayList2.toString());

        System.out.println("Size: " + myArrayList1.size());
        System.out.println("Capacity: " + myArrayList1.capacity());
        System.out.println("isEmpty: " + myArrayList1.isEmpty());
        myArrayList1.clear();
        System.out.println("Después de clear - size: " + myArrayList1.size());
        System.out.println("isEmpty: " + myArrayList1.isEmpty());
    }

    /*public static void eCommerceDiscount () {
        Map<String, DiscountStrategy> strategies = Map.of(
                "NODISCOUNT", new NoDiscount(),
                "FIXED", new FixedAmountDiscount(50_000),
                "PERCENTAGE", new PercentageDiscount(20),
                "NDISCOUNT", new BuyNGetFreeDiscount(4),
                "COUPON", new CouponDiscount("SALE100")
        );

        String descuento1 = "FIXED";
        DiscountStrategy strategy1 = strategies.get(descuento1);
        List<Product> products1 = Arrays.asList(
                new Product("RAM DDR5 32GB",1500000D,"Partes de PC"),
                new Product("Placa Base MSI B850",750000D,"Partes de PC"),
                new Product("Tarjeta Grafica RTX 5060",2000000D,"Partes de PC"),
                new Product("Procesador Ryzen 5 9600X",750000D,"Partes de PC")
        );
        Cart cart1 =  new Cart(products1);

        Order order1 = new Order(strategy1,cart1,"Cliente 1");
        System.out.println("Cliente 1: ");
        processOrder(order1);
        System.out.println("-------------------------------------------");

        String descuento2 = "PERCENTAGE";
        DiscountStrategy strategy2 = strategies.get(descuento2);
        Cart cart2 =  new Cart(products1);

        Order order2 = new Order(strategy2,cart2,"Cliente 2");
        System.out.println("Cliente 2: ");
        processOrder(order2);
        System.out.println("-------------------------------------------");

        String descuento3 = "NDISCOUNT";
        DiscountStrategy strategy3 = strategies.get(descuento3);
        Cart cart3 =  new Cart(products1);

        Order order3 = new Order(strategy3,cart3,"Cliente 3");
        System.out.println("Cliente 3: ");
        processOrder(order3);
        System.out.println("-------------------------------------------");

        String descuento4 = "COUPON";
        DiscountStrategy strategy4 = strategies.get(descuento4);
        Cart cart4 =  new Cart(products1);

        Order order4 = new Order(strategy4,cart4,"Cliente 4");
        System.out.println("Cliente 4: ");
        processOrder(order4);
        System.out.println("-------------------------------------------");
    }*/

    public static void mediaOperations () {

        Book book1 = new Book("La sombra sobre insmounth","Lovecraft",1902,200);
        book1.rate(3);
        book1.rate(4);
        book1.rate(2);
        book1.rate(5);

        Book book2 = new Book("Los mitos de cthulhu","Lovecraft",1911,200);
        book2.rate(4);
        book2.rate(4);
        book2.rate(3);
        book2.rate(5);

        Movie movie1 = new Movie("Avengers: Endgame","Marvel",2018,120);
        movie1.rate(1);
        movie1.rate(2);
        movie1.rate(3);

        Movie movie2 = new Movie("Kimetsu no Yaiba: Infinite Castle","Mappa",2025,150);
        movie2.rate(4);
        movie2.rate(3);
        movie2.rate(3);

        Music music1 = new Music("Metro","SOAD",2009,"Serj",120);
        Music music2 = new Music("Toxicity","SOAD",2009,"Serj",128);

        Podcast podcast1 = new Podcast("Wild Project EP120","Jordi",2024,300);
        Podcast podcast2 = new Podcast("Wild Project EP121","Jordi",2024,300);

        List<Media> medias = Arrays.asList(book1,book2,music1,music2,movie1,movie2,podcast1,podcast2);

        //Filtrar Playables e invocar play
        medias.stream().filter(e -> e instanceof Playable).forEach(o -> ((Playable) o).play());
        System.out.println("_________________________");
        //Mostrar promedio ratable
        System.out.println("Mostrar promedio de los Ratable");
        medias.stream().filter(e -> e instanceof Ratable).map(d -> (Ratable) d).forEach(i -> System.out.println(i.getAverageRating()));
        System.out.println("_________________________");

        //Buscar por tipo: todos los libros
        System.out.println("Busqueda de libros: ");
        medias.stream().filter(m -> m instanceof Book).forEach(System.out::println);
        System.out.println("_________________________");

        //Buscar por titulo
        String tituloBuscar = "mitos";
        System.out.println("Busqueda de libro por titulo: "+tituloBuscar);
        Media media = medias.stream().filter(m -> m.getTitulo().toLowerCase().contains(tituloBuscar.toLowerCase())).findFirst().orElseThrow(() -> new RuntimeException("No se encontró media con título: " + tituloBuscar));
        System.out.println("Media enconrtada: "+ media.toString());
        System.out.println("_________________________");

        //Media con mayor rating promedio
        System.out.println("Media con mayor rating promedio");
        Ratable mediaConMayorProm = medias.stream().filter(m -> m instanceof Ratable).map(o -> (Ratable) o).max(Comparator.comparing(Ratable::getAverageRating)).orElseThrow();
        System.out.println("Media con mayor rating promedio: "+mediaConMayorProm);

    }

    public static void employeeOperations() {
        List<Employee> employees = Arrays.asList(
                new FullTimeEmployee("001","Santiago Marchena","Desarrollo",2000),
                new Contractor("002","Camilo Ospina","Marketing",160,30),
                new PartTimeEmployee("003","Alvaro Morata","Recursos Humanos",120,25),
                new FullTimeEmployee("004","Miguel Alvarado","Desarrollo",3500),
                new PartTimeEmployee("005","Pavel Nevded","Investigacion",120,35)
        );

        //Imprimiendo salario de empleados
        employees.forEach(e -> System.out.println("Employee Salary: " + e.calculateSalary()));

        //Sumar masa salarial total
        double masaTotal = employees.stream().mapToDouble(Employee::calculateSalary).sum();
        System.out.println("Masa Total: " + masaTotal);

        //Filtar taxable y sumar impuestos
        double totalTaxes = employees.stream().filter(e -> e instanceof Taxable)
                .map(e1 -> (Taxable)e1).map(Taxable::calculateTax).reduce(0.0, Double::sum);
        System.out.println("Total Taxes: " + totalTaxes);

        //Empleado mejor pagado
        //max(Comparator.comparable(Employee::calculateSalary))
        Employee maxSalaryEmployee = employees.stream().max(Comparator.comparing(Employee::calculateSalary)).orElseThrow(() -> new RuntimeException("Employee not found"));
        System.out.println("Max Salary Employee: " + maxSalaryEmployee.describe());
    }

    public static void shapesOperations () {
        List<Shape> shapes = Arrays.asList(
                new Circle(5d),
                new Rectangle(5d,6d),
                new Triangle(7d,8d,9d,9d,9d),
                new Circle(6d),
                new Rectangle(18d,17d)
        );

        double areaTotal = shapes.stream().mapToDouble(Shape::area).sum();
        System.out.println("Area Total: " + areaTotal);

        //Opcion "larga"
        //Shape largestShape = shapes.stream().reduce((a1,a2) -> a1.area() < a2.area() ? a2 : a1).orElseThrow();
        //Opcion corta
        Shape largestShape = shapes.stream().max(Comparator.comparingDouble(Shape::area)).orElseThrow();
        System.out.println("Largest Shape: " + largestShape.area());

        List<Shape> shapesOrdenadas = new ArrayList<>(shapes);
        shapesOrdenadas.sort(Comparator.comparing(Shape::area));
        System.out.println("Ordenadas: " + shapesOrdenadas);

        shapes.stream().filter(s -> s instanceof Drawable)
                .map(s -> (Drawable) s).forEach(Drawable::draw);
    }

}
