import static java.lang.Math.sqrt;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.math.*;

public class ManageRSA {

    private BigInteger n, z, p, q, pub, pvt;

    public ManageRSA() {
        setVar(257, 263);
        pub = autoPublic();
        pvt = calcPvt(pub);
        System.out.println("n: " + n);
        System.out.println("z: " + z);
        System.out.println("pub: " + pub);
        System.out.println("pvt: " + pvt);
    }
    
    public static void main(String args[]){
        ManageRSA a = new ManageRSA();
        
        String message = "olar";
        List<BigInteger> cyphered = a.CipherRSA(message);
        
        System.out.println(message 
            + " - " + cyphered 
            + " - " + a.unCipherRSA(cyphered));
    }
    
    public List<BigInteger> CipherRSA(String message){
        int i;
        List<BigInteger> newMessage = new ArrayList<BigInteger>();
        
        for(i=0; i<message.length(); i++){
            BigInteger c = BigInteger.valueOf(Character.getNumericValue(message.charAt(i)));
            System.out.println("Char: " + c.intValue());
            newMessage.add(c.modPow(pub, n));
            //newMessage.add((int)(Math.pow(c, pub) % n));
        }

        return newMessage;
    }
    
    public String unCipherRSA(List<BigInteger> message){
        int i;
        String newMessage = new String();
        
        for(i=0; i<message.size(); i++){
            BigInteger c = message.get(i);
            BigInteger d = c.modPow(pvt, n);
            System.out.println("New Char: " + d.intValue());
            newMessage += Character.toString((char) d.intValue());
        }

        return newMessage;
    }
    
    public void setVar(int p, int q){
        if(checkPrime(p)) this.p = BigInteger.valueOf(p);
        if(checkPrime(q)) this.q = BigInteger.valueOf(q);
        n = this.p.multiply(this.q);
        z = this.p.subtract(BigInteger.valueOf(1)).multiply(this.q.subtract(BigInteger.valueOf(1)));
    }

    public int setPub(BigInteger pub) {
        if(checkPrime(pub.intValue()) && pub.compareTo(z) < 0) {
            this.pub = pub;
            pvt = calcPvt(pub);
            
            return 1;
        }
        
        return -1;
    }

    public BigInteger getN(){
        return n;
    }
    
    public BigInteger getPub() {
        return pub;
    }

    public BigInteger getPvt() {
        return pvt;
    }

    private BigInteger calcPvt(BigInteger pb) {
        BigInteger pv = pub.modInverse(z);
        
        /*while(pb.multiply(pv).mod(z) != BigInteger.valueOf(1)){
            pv = BigInteger.valueOf(getNextPrime(pv.intValue()));
        }*/
        
        return pv;
    }
    
    private BigInteger autoPublic(){
        return BigInteger.valueOf(7);
    }

    private int getNextPrime(int p){
        while(!checkPrime(++p));
        return p;
    }

    private boolean checkPrime(int p) {
        int i;
        
        for(i=2; i<sqrt(p)+1; i++){
            if(p % i == 0) return false;
        }
        
        return true;
    }
}
