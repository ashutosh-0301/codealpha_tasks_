import java.util.HashMap;
import java.util.Map;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String enteredPassword) {
        return password.equals(enteredPassword);
    }
}

class Stock {
    private String symbol;
    private double price;
    private int quantity;

    public Stock(String symbol, double price, int quantity) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    public void updateQuantity(int delta) {
        this.quantity += delta;
    }

    @Override
    public String toString() {
        return "Stock [symbol=" + symbol + ", price=" + price + ", quantity=" + quantity + "]";
    }
}

class StockMarket {
    private Map<String, Stock> stocks;

    public StockMarket() {
        this.stocks = new HashMap<>();
    }

    public void addStock(Stock stock) {
        stocks.put(stock.getSymbol(), stock);
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public void displayStocks() {
        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
    }
}

class StockTradingSystem {
    private StockMarket stockMarket;
    private Map<String, User> users;
    private User currentUser;

    public StockTradingSystem() {
        this.stockMarket = new StockMarket();
        this.users = new HashMap<>();
        this.currentUser = null;
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
            return true;
        } else {
            System.out.println("Login failed. Invalid username or password.");
            return false;
        }
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logout successful.");
    }

    public void buyStock(String symbol, int quantity, double budget) {
        Stock stock = stockMarket.getStock(symbol);
        if (stock != null && stock.getPrice() * quantity <= budget) {
            stock.updateQuantity(quantity);
            System.out.println("Successfully bought " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Unable to buy shares. Insufficient funds or invalid stock symbol.");
        }
    }

    public void sellStock(String symbol, int quantity) {
        Stock stock = stockMarket.getStock(symbol);
        if (stock != null && stock.getQuantity() >= quantity) {
            stock.updateQuantity(-quantity);
            System.out.println("Successfully sold " + quantity + " shares of " + symbol);
        } else {
            System.out.println("Unable to sell shares. Insufficient quantity or invalid stock symbol.");
        }
    }

    public void displayPortfolio() {
        System.out.println("Portfolio for " + currentUser.getUsername() + ":");
        stockMarket.displayStocks();
    }

    public static void main(String[] args) {
        StockTradingSystem tradingSystem = new StockTradingSystem();

        // Adding a sample user

        tradingSystem.addUser(new User("Ashutosh", "password123"));

        // User login example

        tradingSystem.login("Ashutosh", "password123");

        // Adding some sample stocks to the market

        tradingSystem.stockMarket.addStock(new Stock("AAPL", 120.0, 10));
        tradingSystem.stockMarket.addStock(new Stock("GOOGLE", 2500.0, 5));
        tradingSystem.stockMarket.addStock(new Stock("MSFT", 310.0, 8));

        // Displaying initial portfolio

        tradingSystem.displayPortfolio();

        // Buying and selling example

        tradingSystem.buyStock("AAPL", 2, 400.0);
        tradingSystem.sellStock("GOOGLE", 3);

        // Displaying updated portfolio

        tradingSystem.displayPortfolio();

        // User logout example
        
        tradingSystem.logout();
    }
}