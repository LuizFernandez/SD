import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {
    //Uso de Final nas variáveis de Instência para garantir que uma instância Contact é imutavle
    private final String name;
    private final int age;
    private final long phoneNumber;
    private final String company;     // Pode ser null
    private final ArrayList<String> emails;

    public Contact (String name, int age, long phoneNumber, String company, List<String> emails) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.emails = new ArrayList<>(emails);
    }

    public String name() { return name; }
    public int age() { return age; }
    public long phoneNumber() { return phoneNumber; }
    public String company() { return company; }
    public List<String> emails() { return new ArrayList(emails); }

    // @TODO
    public void serialize (DataOutputStream out) throws IOException {
        out.writeUTF(this.name);
        out.writeInt(this.age);
        out.writeLong(this.phoneNumber);
        if(this.company != null) {
            out.writeBoolean(true);
            out.writeUTF(this.company);
        }else
            out.writeBoolean(false);

        out.writeInt(this.emails.size());
        for (String s : this.emails)
            out.writeUTF(s);

        out.flush();
    }

    // @TODO
    public static Contact deserialize (DataInputStream in) throws IOException {
        String name = in.readUTF();
        int age = in.readInt();
        long phoneNumber = in.readLong();

        String company = null;
        if(in.readBoolean())
            company = in.readUTF();

        ArrayList<String> emails = new ArrayList<>();
        String mail;
        int n_emails = in.readInt();
        while(n_emails > 0) {
            mail = in.readUTF();
            emails.add(mail);
            n_emails--;
        }

        return new Contact(name,age,phoneNumber,company, emails);
    }

    public String toString () {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(";\n");
        builder.append(this.age).append(";\n");
        builder.append(this.phoneNumber).append(";\n");
        builder.append(this.company).append(";\n");
        builder.append(this.emails.toString());
        builder.append("\n");
        return builder.toString();
    }

}
