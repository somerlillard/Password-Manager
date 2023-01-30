package assn07;

import java.util.*;

public class PasswordManager<K,V> implements Map<K,V> {
    private static final String MASTER_PASSWORD = "password123";
    private Account[] _passwords;

    public PasswordManager() {
        _passwords = new Account[50];
    }

    // typecasted some stuff
    // TODO: put
    @Override
    public void put(K key, V value) {
        // finding index of the key (where we input new account based on key value)
        int index = Math.abs(key.hashCode()) % 50;
        // if there are no accounts at the index we will place the new account
        if (_passwords[index] == null) {
            Account<K, V> acounty = new Account<K, V>(key, value);
            _passwords[index] = acounty;
        }
        // if there are accounts at the index, we will see if we need to update the current website's value
        else {
            Account<K, V> current = _passwords[index];
            if (current.getWebsite().equals(key)) {
                _passwords[index].setPassword(value);
            }
            // if the current website is not equal to the key but there are more accounts chained to the current one
            else {
                while (current != null) {
                    if (current.getWebsite().equals(key)) {
                        _passwords[index].setPassword(value);
                        return;
                    } else {
                        current = current.getNext();
                    }
                }
                    // when all the websites at the index are different, we add new account to the head (before first node bc we can easily do that)
                    current = _passwords[index];
                    Account<K, V> acounty = new Account<K, V>(key, value);
                    acounty.setNext(current);
                    _passwords[index] = acounty;
            }
        }
    }

    // do i always return the value (password) of the head? index is based on key, so there might be mult accounts at that index w/ a diff key
    // TODO: get
    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode()) % 50;
        if (_passwords[index] != null) {
            Account<K, V> current = _passwords[index];
            while (current != null) {
                if (current.getWebsite().equals(key)) {
                    return current.getPassword();
                } else {
                    current = current.getNext();
                }
            }
        }
        return null;
    }

    // TODO: size
    @Override
    public int size() {
        int s = 0;
        for (int i = 0; i < _passwords.length; i++) {
            Account<K, V> current = _passwords[i];
            while (current != null) {
                s++;
                current = current.getNext();
            }
        }
        return s;
    }

    // typecasting
    // TODO: keySet
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<K>();
        for (int i = 0; i < _passwords.length; i++) {
            Account<K, V> current = _passwords[i];
            while (current != null) {
                keys.add((current.getWebsite()));
                current = current.getNext();
            }
        }
        return keys;
    }

    // think of cases where remove - empty index^, one acc index^, mult acc index remove mid, mult acc index remove last, mult acc index remove first
    // TODO: remove
    @Override
    public V remove(K key) {
        // finding index of key-value to remove
        int index = Math.abs(key.hashCode()) % 50;
        Account current = _passwords[index];
        if (current != null) {
            // creating a var for the size of an account's chain
//        while (current != null) {
//            index_size++;
//            current = current.getNext();
//        }
//        current = _passwords[index];
            // iterating through an account's chain while keeping track of which node came before
//                for (int i = 0; i < index_size; i++) {
//
//                }
            // if key/website found
            if (current.getWebsite().equals(key)) {
                V val = (V) current.getPassword();
                Account<V, K> next = current.getNext();
                _passwords[index] = next;
                current.setWebsite(null);
                current.setPassword(null);
                return val;
            } else {
                while (current.getNext() != null) {
                    if (current.getNext().getWebsite().equals(key)) {
                        V val = (V) current.getNext().getPassword();
                        current.setNext(current.getNext().getNext());
                        return val;
                    }
                    current = current.getNext();
                }
            }
        }
        return null;
    }

    // typecasting
    // TODO: checkDuplicate
    @Override
    public List<K> checkDuplicate(V value) {
        List<K> weblist = new ArrayList<>();
        for (int i = 0; i < _passwords.length; i++) {
            Account<K, V> current = _passwords[i];
                    while (current != null) {
                        if (current.getPassword().equals(value)) {
                            weblist.add((current.getWebsite()));
                        }
                        current = current.getNext();
                    }
                }
        return weblist;
    }


    // DONE
    @Override
    public boolean checkMasterPassword(String enteredPassword) {
        return enteredPassword.equals(MASTER_PASSWORD);
    }
    /*
    Generates random password of input length
     */
    @Override
    public String generateRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    /*
    Used for testing, do not change
     */
    public Account[] getPasswords() {
        return _passwords;
    }
}
