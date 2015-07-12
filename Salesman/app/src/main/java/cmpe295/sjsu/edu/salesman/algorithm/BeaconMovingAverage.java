package cmpe295.sjsu.edu.salesman.algorithm;

import android.support.v4.util.CircularArray;

/**
 * Created by jijhaver on 6/21/15.
 */
public class BeaconMovingAverage implements  IMovingAverage{
    public static final  int MAX_CAPACITY = 8;
    CircularArray<Double> samples;
    protected Double total;


    /// <summary>
    /// Get the average for the current number of samples.
    /// </summary>
    public Double getAverage() {

        if (samples.isEmpty())
        {
           /* try {
                throw new Exception("Number of samples is 0.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
            return 1.0;
        }
        return total / samples.size();
    }

    /// <summary>
    /// Constructor, initializing the sample size to the specified number.
    /// </summary>
    public BeaconMovingAverage()
    {
        samples = new CircularArray<Double>(MAX_CAPACITY);
        total = 0.0;
    }

    @Override
    public void addSample(Double val) {
        if (samples.size() >= MAX_CAPACITY)
        {
            total -= samples.popFirst();
        }
        samples.addLast(val);
        total += val;

    }

    @Override
    public void clearSamples() {
        total = 0.0;
        samples.clear();
    }

}
