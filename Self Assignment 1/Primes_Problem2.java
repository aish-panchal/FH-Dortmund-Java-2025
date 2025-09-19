public class Primes_Problem2
{
    public static void main(String[] args)
    {
	int nValues = 47;

	int loop_limit = (int)Math.sqrt(nValues);
	int i = 1;
	out:
	while(i == 1){
	    i++;
	    for (; i < loop_limit; i++)
		{
		    if (nValues % i == 0){
			continue out;
		    }
		}
	}
	if (i == loop_limit){
	    System.out.println(nValues + " is prime");
	} else {
	    System.out.println(nValues + " divides by " + i);
	}
    }
}
