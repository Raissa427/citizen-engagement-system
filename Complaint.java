import java.util.*;

// Entity class (like @Entity in Spring Boot)
class Complaint {
    private static int counter = 1;

    private int id;
    private String citizenName;
    private String category;
    private String description;
    private String status;
    private String assignedAgency;

    public Complaint(String citizenName, String category, String description) {
        this.id = counter++;
        this.citizenName = citizenName;
        this.category = category;
        this.description = description;
        this.status = "New";
        this.assignedAgency = routeToAgency(category);
    }

   private String routeToAgency(String category) {
       Map<String, String> routing = new HashMap<>();
       routing.put("Roads", "Ministry of Infrastructure");
       routing.put("Water", "Water Department");
       routing.put("Electricity", "Energy Board");
       routing.put("Health", "Ministry of Health");
       routing.put("Others", "General Services");

       return routing.getOrDefault(category, "General Services");
   }

    public int getId() { return id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Ticket #" + id + " | " + category + " | Status: " + status +
               "\nCitizen: " + citizenName +
               "\nAssigned to: " + assignedAgency +
               "\nDescription: " + description + "\n";
    }
}

// Service class (like @Service in Spring Boot)
class ComplaintService {
    private static final String URL = "jdbc:mysql://localhost:3306/banking";
    private static final String USER = "root";
    private static final String PASSWORD = "RaissaRwanda 20";
    private final List<Complaint> complaintDB = new ArrayList<>();

    // Create complaint (POST)
    public Complaint submitComplaint(String name, String category, String description) {
        Complaint complaint = new Complaint(name, category, description); 
        complaintDB.add(complaint);
        return complaint;
    }

    // Read all complaints (GET)
    public List<Complaint> getAllComplaints() {
        return complaintDB;
    }

    // Read single complaint by ID (GET /{id})
    public Complaint getComplaintById(int id) {
        return complaintDB.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Update complaint status (PUT /{id})
    public boolean updateStatus(int id, String status) {
        Complaint c = getComplaintById(id);
        if (c != null) {
            c.setStatus(status);
            return true;
        }
        return false;
    }

    // Delete complaint (DELETE /{id})
    public boolean deleteComplaint(int id) {
        return complaintDB.removeIf(c -> c.getId() == id);
    }
}