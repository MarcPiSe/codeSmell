package org.udg.caes.stockmarket;

/**
 * Created by imartin on 14/12/16.
 */
public class Silver {
  double mAmountOz;
  double mPriceOz;

  public Silver(double mAmountOz, double mPriceOz) {
    this.mAmountOz = mAmountOz;
    this.mPriceOz = mPriceOz;
  }

  public double getmAmountOz() {
    return mAmountOz;
  }

  public void setmAmountOz(double mAmountOz) {
    this.mAmountOz = mAmountOz;
  }

  public double getmPriceOz() {
    return mPriceOz;
  }

  public void setmPriceOz(double mPriceOz) {
    this.mPriceOz = mPriceOz;
  }

  double getValuation() { return mAmountOz * mPriceOz; }
}
