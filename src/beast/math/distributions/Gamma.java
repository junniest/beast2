package beast.math.distributions;


import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.GammaDistributionImpl;

import beast.core.Description;
import beast.core.Input;
import beast.core.parameter.RealParameter;

@Description("Gamma distribution. for x>0  g(x;alpha,beta) = 1/Gamma(alpha) beta^alpha} x^{alpha - 1} e^{-\frac{x}{beta}}" +
        "If the input x is a multidimensional parameter, each of the dimensions is considered as a " +
        "separate independent component.")
public class Gamma extends ParametricDistribution {
    final public Input<RealParameter> alphaInput = new Input<>("alpha", "shape parameter, defaults to 2");
    final public Input<RealParameter> betaInput = new Input<>("beta", "scale parameter, defaults to 2");

    static org.apache.commons.math.distribution.GammaDistribution m_dist = new GammaDistributionImpl(1, 1);

    @Override
    public void initAndValidate() {
        refresh();
    }

    /**
     * make sure internal state is up to date *
     */
    @SuppressWarnings("deprecation")
	void refresh() {
        double alpha;
        double beta;
        if (alphaInput.get() == null) {
            alpha = 2;
        } else {
            alpha = alphaInput.get().getValue();
        }
        if (betaInput.get() == null) {
            beta = 2;
        } else {
            beta = betaInput.get().getValue();
        }
        m_dist.setAlpha(alpha);
        m_dist.setBeta(beta);
    }

    @Override
    public ContinuousDistribution getDistribution() {
        refresh();
        return m_dist;
    }

    @Override
    public double getMean() {
    	return offsetInput.get() + m_dist.getAlpha() * m_dist.getBeta();
    }
} // class Gamma
