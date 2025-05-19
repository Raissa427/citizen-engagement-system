
import java.util.List;
import java.util.Scanner;
// Controller Simulation (like @RestController)
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ComplaintService service = new ComplaintService();

        while (true) {
            System.out.println("\nCitizen Complaint System - Menu");
            System.out.println("1. Submit Complaint");
            System.out.println("2. View All Complaints");
            System.out.println("3. Track Complaint by ID");
            System.out.println("4. Update Complaint Status");
            System.out.println("5. Delete Complaint");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Citizen Name: ");
                    String name = sc.nextLine();
                    System.out.print("Category: ");
                    String category = sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();
                    Complaint newComplaint = service.submitComplaint(name, category, desc);
                    System.out.println("Complaint Submitted. Ticket ID: " + newComplaint.getId());
                    break;

                case 2:
                    List<Complaint> all = service.getAllComplaints();
                    if (all.isEmpty()) {
                        System.out.println("No complaints found.");
                    } else {
                        all.forEach(System.out::println);
                    }
                    break;

                case 3:
                    System.out.print("Enter Ticket ID: ");
                    int trackId = sc.nextInt();
                    Complaint found = service.getComplaintById(trackId);
                    if (found != null) {
                        System.out.println(found);
                    } else {
                        System.out.println("Complaint not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Ticket ID: ");
                    int updateId = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter New Status: ");
                    String status = sc.nextLine();
                    boolean updated = service.updateStatus(updateId, status);
                    System.out.println(updated ? "Status Updated." : "Complaint not found.");
                    break;

                case 5:
                    System.out.print("Enter Ticket ID: ");
                    int deleteId = sc.nextInt();
                    boolean deleted = service.deleteComplaint(deleteId);
                    System.out.println(deleted ? "Complaint Deleted." : "Complaint not found.");
                    break;

                case 6:
                    System.out.println("Exiting.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}