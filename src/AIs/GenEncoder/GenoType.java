package AIs.GenEncoder;

/** A genotype is consistent of Genestrings
 * Those genestrings are encoded as Permutations
 * Therefore our code is a permutationmatrix
 *
 * Tetraploidie chromosome string where 4 permutations are existent within one Chromosome
 * Created by Nibbla on 15.10.2016.
 */
public abstract class GenoType {
    abstract Permutation getPermutation(int geneIndex, int chromeIndex);

}
