package main;

import java.util.*;

public final class AdresnayaKniga {

    private Map<String, Adress> spisok = new HashMap<>();
    private Map<String, Map<String, Set<String>>> dlyaPoiska = new HashMap<>();


    public void add(String familiya, Adress adres) {
        if (spisok.putIfAbsent(familiya, adres) != null) throw new IllegalArgumentException();
        dlyaPoiska.computeIfAbsent(adres.getYlitsa(), key -> new HashMap<>()).computeIfAbsent(adres.getNomerdoma(), key -> new HashSet<>()).add(familiya);
    }

    public boolean removeHuman(String familiya) {
        Adress a = findAddress(familiya);
        if (spisok.remove(familiya) == null) return false;
        dlyaPoiska.get(a.getYlitsa()).get(a.getNomerdoma()).remove(familiya);
        return true;
    }

    public void izmenenieAdresa(String familiya, Adress adres) {
        Adress a = findAddress(familiya);
        Map<String, Set<String>> map = dlyaPoiska.get(a.getYlitsa());
        if (map == null) throw new IllegalArgumentException() ;
        Set<String> set = map.get(a.getNomerdoma());
        if (set == null) throw new IllegalArgumentException();
        spisok.remove(familiya);
        spisok.putIfAbsent(familiya, adres);
        dlyaPoiska.computeIfAbsent(adres.getYlitsa(), key -> new HashMap<>()).computeIfAbsent(adres.getNomerdoma(), key -> new HashSet<>()).add(familiya);
    }

    public Adress findAddress (String familiya) {
        return spisok.get(familiya);
    }

    public Set<String> findHumans (String ylitsa, String nomerdoma) {
        Map<String, Set<String>> map = dlyaPoiska.get(ylitsa);
        if (map == null) return new HashSet<>();
        return new HashSet<>(map.get(nomerdoma));
    }

    public Set<String> findHumans (String ylitsa) {
        Set<String> result = new HashSet<>();
        Map<String, Set<String>> map = dlyaPoiska.get(ylitsa);
        if (map == null) return new HashSet<>();
        for (Set<String> s : map.values()) {
            result.addAll(s);
        }
        return result;
    }


    public static void main(String[] args) {

    }

}
