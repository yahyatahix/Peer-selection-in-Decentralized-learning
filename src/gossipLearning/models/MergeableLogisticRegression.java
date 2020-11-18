package gossipLearning.models;

import peersim.config.Configuration;
import gossipLearning.interfaces.Mergeable;
import gossipLearning.utils.SparseVector;

public class MergeableLogisticRegression extends LogisticRegression implements Mergeable<MergeableLogisticRegression>{
  private static final long serialVersionUID = -4465428750554412761L;
  
  protected static final String PAR_LAMBDA = "MergeableLogisticRegression.lambda";

  public MergeableLogisticRegression(){
    super();
  }
  
  /**
   * Returns a new mergeable logistic regression object that initializes its variable with 
   * the deep copy of the specified parameters using the super constructor.
   * @param w hyperplane
   * @param age model age
   * @param lambda learning parameter
   */
  protected MergeableLogisticRegression(SparseVector w, double age, double lambda, int numberOfClasses, double bias){
    super(w, age, lambda, numberOfClasses, bias);
  }
  
  public Object clone(){
    return new MergeableLogisticRegression(w, age, lambda, numberOfClasses, bias);
  }
  
  public void init(String prefix) {
    super.init(prefix);
    lambda = Configuration.getDouble(prefix + "." + PAR_LAMBDA, 0.0001);
  }
  
  @Override
  public MergeableLogisticRegression merge(final MergeableLogisticRegression model) {
    SparseVector mergedw = new SparseVector(w);
    double age = Math.max(this.age, model.age);
    double bias = (this.bias + model.bias) / 2.0;
    mergedw.mul(0.5);
    mergedw.add(model.w, 0.5);
    
    return new MergeableLogisticRegression(mergedw, age, lambda, numberOfClasses, bias);
  }
}
