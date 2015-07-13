package cmpe295.sjsu.edu.salesman.algorithm;

/**
 * Created by jijhaver on 6/21/15.
 */
public interface IMovingAverage
{
        Double getAverage();

        void addSample(Double val);
        void clearSamples();
}

