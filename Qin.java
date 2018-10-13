import java.math.BigInteger;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;


public class Qin {

	private static BigInteger d;
	private BigInteger p;
	private BigInteger q;
	private static BigInteger attack_input;
	private static BigInteger temp;
	private static BigInteger n;
	private static BigInteger e = BigInteger.valueOf(65537);

	public void initialize() {
		int prime_size = 2048;
		p = BigInteger.probablePrime(prime_size,  new Random());
		q = BigInteger.probablePrime(prime_size, new Random());
		n = p.multiply(q);
		temp = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

	}
	public static BigInteger Qin_algo(BigInteger publickey_e, BigInteger publickey_n , boolean attack) {
		BigInteger X[][] = new BigInteger[4][4];
		BigInteger q_quotient , q_remainder, q_result = null;

		X[0][0] = BigInteger.ONE;
		X[0][1] = publickey_e;
		X[1][0] = BigInteger.ZERO;
		X[1][1] = publickey_n;

		while (X[0][1].compareTo(BigInteger.ONE) == 1) {

			if (X[1][1].compareTo(X[0][1]) == 1) {
				q_quotient = X[1][1].subtract(BigInteger.ONE).divide(X[0][1]);
				q_remainder = X[1][1].subtract(q_quotient.multiply(X[0][1]));
				X[1][0] = q_quotient.multiply(X[0][0]).add(X[1][0]);

				if (attack == true) {
					d = X[1][0];
					BigInteger bct = encrypt(attack_input);
					BigInteger  pt_after = decrypt(bct);
					if (attack_input.equals(pt_after)) {
						System.out.println("\nAfter Attack Decrypted Text is : " + new String(pt_after.toByteArray()));
						System.out.println("Cracked Private Key (D) :" + d);
						break;
					}
				}
				X[1][1] = q_remainder;

				if (X[0][1].compareTo(X[1][1]) == 1) {
					q_quotient = X[0][1].subtract(BigInteger.ONE).divide(X[1][1]);
					q_remainder = X[0][1].subtract(q_quotient.multiply(X[1][1]));
					X[0][0] = q_quotient.multiply(X[1][0]).add(X[0][0]);
					X[0][1] = q_remainder;
				}
				q_result = X[0][0];
			}
		}
		return q_result;
	}

	public static BigInteger encrypt(BigInteger pt) {
		return pt.modPow(e, n);
	}
	public static BigInteger decrypt(BigInteger ct) {
		return ct.modPow(d, n);
	}

	public BigInteger get_Message() {
		String message;
		System.out.println("\nEnter any message : ");
		Scanner scanner = new Scanner(System.in);
		message = scanner.nextLine();
		BigInteger bpt;
		bpt = new BigInteger(message.getBytes());
		return bpt;
	}

	public static void main(String[] args) throws IOException {
		Qin rsa = new Qin();
		BigInteger bptext, bct;
		bptext = rsa.get_Message();
		rsa.initialize();
		d = Qin_algo(e, temp, false);
		bct = rsa.encrypt(bptext);
		System.out.println("\nCipher Text : " + bct.toString());
		BigInteger pt_after = rsa.decrypt(bct);

		
		System.out.println("\nWeiner attack in progress.." );
		String anim = "|/-\\";
		for (int x = 0 ; x < 101 ; x++) {
			String text = "\r" + anim.charAt(x % anim.length()) + " " + x;
			System.out.write(text.getBytes());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
		attack_input = bptext;
		BigInteger NN = new BigInteger ("13944220401877938113014934848099117271555842144465711135230872813021395268695304462884287334296659283504546204155028953304304152419500276351517026203090560312682332100557949888340674488347457083400825956841368247339132128545661778946404948547066296060177763630405864758766614366952316600616993369171973988711601072880358006049128862543088069228671596572559102238313290682720666719883296361722526485195136936001869087770636745643885209795529748130091587883621035733051140981054393173745532828558933056050658667881198651606339538768702135427015143742705493428714835468242036488501979845083076916758773475058112957196487");
		n = NN;
		BigInteger EE = new BigInteger("3728438759195168737135992109727249348803612435894359189081738936348226606690336984297449194984522245267347608647851637838095872369329569971857522894319893342321565064748765439590130905828618062110655958711360104101926567503619653408915623418784429679330189884729041367661885435565586952318599099463080887219162354874117983707707854892379461221207015463083472995141690891986170606756925711854474324916913314927750891337011838020040295424415142917065939663073173249929035030071510843219620490640863993468878684803620906049125234829708750801749587906700784584868164495039453453030247421811968896558706354352374798351579");
		e = EE;
		d = Qin_algo(e, n, true);

	}
}