package k_means;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.util.*;

class SVD {
	private Matrix M;
	private Matrix U;
	private Matrix S;
	private Matrix USigTransp;

	public double[][] convertto2d(List<tfidfvalues> matrix) {
		Integer rows = matrix.size();
		Integer columns = matrix.get(0).returntfidfvaluesforrow().size();

		double[][] newmatrix = new double[rows][columns];

		for(int i = 0; i < matrix.size(); i++) {
			ArrayList<Double> row = matrix.get(i).returntfidfvaluesforrow();
			double[] row2v = new double[row.size()];
			Iterator<Double> iterator = row.iterator();
			int j = 0;
			while(iterator.hasNext()) {
				row2v[j] = iterator.next();
				j++;
			}

			newmatrix[i] = row2v;
		}

		return newmatrix;
	}

	public void converttomatrix(double[][] matrix) {
		M = new Matrix(matrix);
	}

	public Matrix returnmatrix() {
		return M;
	}

	public void doeverything() {
		Matrix matrix = M.transpose();
		SingularValueDecomposition svdmatrix = matrix.svd();

		U = svdmatrix.getV().transpose();
		S = svdmatrix.getS().transpose();

		Matrix USig = U.times(S);

		USigTransp = USig.transpose();
	}

	public Matrix returnUSigTransp(){
		return USigTransp;
	}

	public double[][] return2d(){
		Integer numberofrows = USigTransp.getRowDimension();
		Matrix reducedmatrix = USigTransp.getMatrix(0, numberofrows - 1, 0, 1);
		return reducedmatrix.getArray();
	}

	public double[][] returntopd() {
		Integer numberofrows = USigTransp.getRowDimension();
		Matrix reducedmatrix = USigTransp.getMatrix(0, numberofrows - 1, 0, 14);
		return reducedmatrix.getArray();
	}
	
	public double[][] returnsigma() {
		Integer numberofrows = S.getRowDimension();
		Integer numberofcolumns = S.getColumnDimension();
		Matrix sigmamatrix = S.getMatrix(0, numberofrows - 1, 0, numberofcolumns - 1);
		return sigmamatrix.getArray();
	}
	
	public Integer sigmarows() {
		return S.getRowDimension();
	}
	
	public Integer sigmacolumns() {
		return S.getColumnDimension();
	}
}