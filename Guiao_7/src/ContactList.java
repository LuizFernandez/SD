import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class ContactList extends ArrayList<Contact> {

    // @TODO
    public void serialize (DataOutputStream out) throws IOException {

        out.writeInt(this.size());
        for(int i = 0; i < this.size(); i++){
            this.get(i).serialize(out);
        }

    }

    // @TODO
    public static ContactList deserialize (DataInputStream in) throws IOException {

        ContactList lContac = new ContactList();

        int size = in.readInt();
        for(int i = 0; i < size; i++)
            lContac.add(i,Contact.deserialize(in));

        return lContac;
    }

}
