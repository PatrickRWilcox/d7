package d7;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SelectionSorter<E> extends Sorter {

	public SelectionSorter(Comparator cmp) {
		super(cmp);
	}

	@Override
	public void sort(List a) {
		// invariant: 0 i length
		// a: [ sorted, smaller then rest | ? ]

		int i = 0;
		// initialization: a[0..0) is empty, thus sorted

		while (i < a.size()) {
			i++; // progress: i increases
			int k = indexOfMin(a, i - 1, a.size());
			swap(a, i - 1, k);
			// preservation: a[i-1] was originally the minimum value of a[i-1...length)
			// so it is smaller than all the rest.
		}
		// termination: i == length, so a[0..length) is a[0..i) which is sorted
	}

	/** returns index of the smallest value of a[start...end) */
	private int indexOfMin(List<E> a, int start, int end) {
		// invariant: start min i end
		// a: [ >= x | x | >= x | ? ]

		int min = start, i = start + 1;
		// initialization: a[start..min) and a[min+1..i) are empty

		while (i < end) {
			i++; // progress: i increasing

			// start min i end
			// a: [ >= x | x | >= x |?| ? ]
			if (compare(a, i - 1, min) < 0)
				min = i - 1;

			// preservation: know that a[i-1] >= a[min]
		}
		// termination: i == end, so a[start..i) is a[start..end), and all
		// values are >= x
		return min;

	}

	public static class Tests extends Sorter.Tests {
		public SelectionSorter<Integer> sorter() {
			return new SelectionSorter<Integer>(Comparator.naturalOrder());
		}

		@Test
		public void testSort() {
			Sorter<Integer> s = sorter();
			List<Integer> a = testCase();
			s.sort(a);
			assertEquals(testCaseSorted(), a);
		}
	}
}