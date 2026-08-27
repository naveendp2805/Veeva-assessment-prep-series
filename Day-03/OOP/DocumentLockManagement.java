import java.time.Instant;
import java.util.*;

class User
{
    private int userId;
    private String userName;

    public User(int userId, String userName) {
        this.userName = userName;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}

class Document
{
    private int docId;
    private User user;
    private long timeStamp;
    private boolean isLock;

    public Document(int docId)
    {
        this.docId = docId;
        this.user = null;
        this.timeStamp = 0L;
        this.isLock = false;
    }

    public int getDocId() {
        return docId;
    }

    public User getUser() {
        return user;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setuser(User user) {
        this.user = user;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setIsLock(boolean bool) {
        this.isLock = bool;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(docId);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Document doc = (Document) obj;

        return docId == doc.getDocId();
    }
}

class DocumentLockManager
{
    HashSet<Document> docs = new HashSet<>();

    public DocumentLockManager(HashSet<Document> docs) {
        this.docs = docs;
    }

    public void requestLock(int docId, User user, long timeStamp) 
    {
        for(Document doc : docs)
        {
            if(doc.getDocId() == docId)
            {
                if(!doc.isLock())
                {
                    doc.setuser(user);
                    doc.setTimeStamp(timeStamp);
                    doc.setIsLock(true);

                    System.out.println("Doc ID: " + doc.getDocId() + " is locked with user: " + doc.getUser().getUserName());

                    return ;
                } else {
                    System.out.println("Doc Id: " + doc.getDocId() + " is already locked with user: " + doc.getUser().getUserName());
                    return ;
                }   
            }
        }

        System.out.println("No Doc Id found!");
    }

    public void releaseLock(int docId, User user) {
        
        for(Document doc : docs)
        {
            if(doc.getDocId() == docId)
            {
                if(doc.isLock())
                {
                    if(doc.getUser().getUserId() == user.getUserId())
                    {
                        doc.setuser(null);
                        doc.setTimeStamp(0L);
                        doc.setIsLock(false);

                        System.out.println("Lock removed for the DocId: " + doc.getDocId());
                        return ;
                    } else
                    {
                        System.out.println("Invalid user for the given Document with docId: " + doc.getDocId());
                        return ;
                    }
                } else {
                    System.out.println("Doc Id: " + doc.getDocId() + " has no lock with user: " + doc.getUser().getUserName());
                    return ;
                }   
            }
        }

        System.out.println("No Doc Id found!");
    }

    public void clearExpiredLocks(long currTimestamp, long duration)
    {
        for(Document doc : docs)
        {
            if(doc.isLock() && currTimestamp - doc.getTimeStamp() > duration)
            {
                doc.setuser(null);
                doc.setTimeStamp(0L);
                doc.setIsLock(false);

                System.out.println("Lock removed for the DocId: " + doc.getDocId());
            }
        }
    }
}

public class DocumentLockManagement
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        User user1 = new User(1, "Naveen");
        User user2 = new User(2, "Rahul");
        User user3 = new User(3, "Kiran");

        Document doc1 = new Document(101);
        Document doc2 = new Document(102);
        Document doc3 = new Document(103);

        HashSet<Document> docs = new HashSet<>();
        docs.add(doc1);
        docs.add(doc2);
        docs.add(doc3);

        DocumentLockManager manager = new DocumentLockManager(docs);

        while(true)
        {
            System.out.println("\nDocument Lock Management");
            System.out.println("1: Request Lock");
            System.out.println("2: Release Lock");
            System.out.println("3: Clear Expired Locks");
            System.out.println("4: Exit");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            if(ch == 1)
            {
                System.out.print("Enter Document ID: ");
                int docId = sc.nextInt();

                System.out.print("Enter User ID: ");
                int userId = sc.nextInt();

                User user = null;

                if(userId == 1)
                    user = user1;
                else if(userId == 2)
                    user = user2;
                else if(userId == 3)
                    user = user3;
                else
                {
                    System.out.println("Invalid User ID!");
                    continue;
                }

                long timestamp = Instant.now().toEpochMilli();

                manager.requestLock(docId, user, timestamp);
            }
            else if(ch == 2)
            {
                System.out.print("Enter Document ID: ");
                int docId = sc.nextInt();

                System.out.print("Enter User ID: ");
                int userId = sc.nextInt();

                User user = null;

                if(userId == 1)
                    user = user1;
                else if(userId == 2)
                    user = user2;
                else if(userId == 3)
                    user = user3;
                else
                {
                    System.out.println("Invalid User ID!");
                    continue;
                }

                manager.releaseLock(docId, user);
            }
            else if(ch == 3)
            {
                long currentTimestamp = Instant.now().toEpochMilli();

                System.out.print("Enter expiration duration in milliseconds: ");
                long duration = sc.nextLong();

                manager.clearExpiredLocks(currentTimestamp, duration);
            }
            else if(ch == 4)
            {
                System.out.println("Exiting...");
                break;
            }
            else
            {
                System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
