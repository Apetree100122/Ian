package com.walkertribe.ian.world;

import java.util.SortedMap;

import com.walkertribe.ian.enums.BeamFrequency;
import com.walkertribe.ian.util.BoolState;

/**
 * Base implementation for ships (player or NPC).
 */
public abstract class BaseArtemisShip extends BaseArtemisShielded {
    private float mVelocity = Float.NaN;
    private float mShieldsFrontMax = Float.NaN;
    private float mShieldsRearMax = Float.NaN;
    private final float[] mShieldFreqs = new float[5];
    private float mSteering = Float.NaN;
    private float mTopSpeed = Float.NaN;
    private float mTurnRate = Float.NaN;
    private float mImpulse = Float.NaN;
    private byte mSide = -1;
    private Integer mVisibility;

    public BaseArtemisShip(int objId) {
        super(objId);

        for (int i = 0; i < 5; i++) {
        	mShieldFreqs[i] = Float.NaN;
        }
    }

    /**
     * Current speed of the ship: 0.0 = all stop, 1.0 = full speed
     * Unspecified: Float.NaN
     */
    public float getVelocity() {
        return mVelocity;
    }
    
    public void setVelocity(float velocity) {
        mVelocity = velocity;
    }

    /**
     * Rudder position for this ship: 0.0 = hard to port, 0.5 = rudder
     * amidships, 1.0 hard to starboard
     * Unspecified: Float.NaN
     */
    public float getSteering() {
        return mSteering;
    }

    public void setSteering(float steeringSlider) {
        mSteering = steeringSlider;
    }

    /**
     * The maximum speed of this ship, in ls (whatever that is).
     * Unspecified: -1
     */
    public float getTopSpeed() {
        return mTopSpeed;
    }

    public void setTopSpeed(float topSpeed) {
        mTopSpeed = topSpeed;
    }
    
    /**
     * The maximum turn rate of this ship.
     * Unspecified: Float.NaN
     */
    public float getTurnRate() {
        return mTurnRate;
    }
    
    public void setTurnRate(float turnRate) {
        mTurnRate = turnRate;
    }

    /**
     * The maximum strength of the forward shield.
     * Unspecified: Float.NaN
     */
    public float getShieldsFrontMax() {
        return mShieldsFrontMax;
    }

    public void setShieldsFrontMax(float shieldsFrontMax) {
        this.mShieldsFrontMax = shieldsFrontMax;
    }
    
    /**
     * The maximum strength of the aft shield.
     * Unspecified: Float.NaN
     */
    public float getShieldsRearMax() {
        return mShieldsRearMax;
    }

    public void setShieldsRearMax(float shieldsRearMax) {
        this.mShieldsRearMax = shieldsRearMax;
    }

    /**
     * A value between 0 and 1 indicating the shields' resistance to the given
     * BeamFrequency. Higher values indicate that the shields are more resistant
     * to that frequency.
     * Unspecified: Float.NaN
     */
    public float getShieldFreq(BeamFrequency freq) {
        return mShieldFreqs[freq.ordinal()];
    }
    
    public void setShieldFreq(BeamFrequency freq, float value) {
        mShieldFreqs[freq.ordinal()] = value;
    }

    /**
     * Impulse setting, as a value from 0 (all stop) and 1 (full impulse).
     * Unspecified: Float.NaN
     */
    public float getImpulse() {
        return mImpulse;
    }

    public void setImpulse(float impulseSlider) {
        mImpulse = impulseSlider;
    }

    /**
     * The side this ship is on. There is no side 0. Biomechs are side 30.
     */
    public byte getSide() {
    	return mSide;
    }

    public void setSide(byte side) {
    	mSide = side;
    }

    /**
     * Returns whether this ship is visible to the given side on map screens.
     * Unspecified: UNKNOWN
     */
    public BoolState getVisibility(int side) {
    	return mVisibility == null ? BoolState.UNKNOWN : BoolState.from((mVisibility & (1 << side)) == 1);
    }

    /**
     * Sets the visibility of this ship for the indicated side.
     */
    public void setVisibility(int side, boolean visible) {
    	if (mVisibility == null) {
    		mVisibility = 0;
    	}

    	mVisibility |= 1 << side;
    }

    /**
     * Returns the raw bits for visibility.
     */
    public Integer getVisibilityBits() {
    	return mVisibility;
    }

    /**
     * Sets the raw bits for visibility.
     */
    public void setVisibilityBits(int bits) {
    	mVisibility = bits;
    }

    @Override
    public void updateFrom(ArtemisObject obj) {
        super.updateFrom(obj);
        
        if (obj instanceof BaseArtemisShip) {
            BaseArtemisShip ship = (BaseArtemisShip) obj;
            
            if (!Float.isNaN(ship.mSteering)) {
                mSteering = ship.mSteering;
            }
            
            if (!Float.isNaN(ship.mVelocity)) {
                mVelocity = ship.mVelocity;
            }

            if (!Float.isNaN(ship.mTopSpeed)) {
                mTopSpeed = ship.mTopSpeed;
            }

            if (!Float.isNaN(ship.mTurnRate)) {
                mTurnRate = ship.mTurnRate;
            }
            
            if (!Float.isNaN(ship.mShieldsFrontMax)) {
                mShieldsFrontMax = ship.mShieldsFrontMax;
            }

            if (!Float.isNaN(ship.mShieldsRearMax)) {
                mShieldsRearMax = ship.mShieldsRearMax;
            }

            if (!Float.isNaN(ship.mImpulse)) {
            	mImpulse = ship.mImpulse;
            }

            if (ship.mSide != -1) {
            	mSide = ship.mSide;
            }

            for (int i = 0; i < mShieldFreqs.length; i++) {
            	float value = ship.mShieldFreqs[i];

            	if (!Float.isNaN(value)) {
                    mShieldFreqs[i] = value;
            	}
            }
        }
    }

    @Override
	public void appendObjectProps(SortedMap<String, Object> props) {
    	super.appendObjectProps(props);
    	putProp(props, "Velocity", mVelocity);
    	putProp(props, "Shields: fore max", mShieldsFrontMax);
    	putProp(props, "Shields: aft max", mShieldsRearMax);
    	BeamFrequency[] freqs = BeamFrequency.values();

    	for (int i = 0; i < mShieldFreqs.length; i++) {
    		putProp(props, "Shield frequency " + freqs[i], mShieldFreqs[i]);
    	}

    	putProp(props, "Rudder", mSteering);
    	putProp(props, "Top speed", mTopSpeed);
    	putProp(props, "Turn rate", mTurnRate);
    	putProp(props, "Impulse", mImpulse);
    	putProp(props, "Side", mSide, -1);
    }

    /**
     * Returns true if this object contains any data.
     */
    protected boolean hasData() {
    	if (super.hasData()) {
    		return true;
    	}

    	if (
    			!Float.isNaN(mVelocity) ||
    			!Float.isNaN(mShieldsFrontMax) ||
    			!Float.isNaN(mShieldsRearMax) ||
    			!Float.isNaN(mSteering) ||
    			!Float.isNaN(mTopSpeed) ||
    			!Float.isNaN(mTurnRate) ||
    			!Float.isNaN(mImpulse) ||
    			mSide != -1
    	) {
    		return true;
    	}

    	for (float freq : mShieldFreqs) {
    		if (!Float.isNaN(freq)) {
    			return true;
    		}
    	}

    	return false;
    }
}