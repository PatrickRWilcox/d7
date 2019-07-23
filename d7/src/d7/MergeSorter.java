package d7;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MergeSorter<E> extends Sorter {

	public MergeSorter(Comparator cmp) {
		super(cmp);
	}

	@Override
	public void sort(List a) {
		sort(a, 0, a.size());
	}

	/**
	 * sort a[start..end) and leave rest of a alone post condition: start end a: [ |
	 * sorted | ]
	 * 
	 * @param a
	 * @param start
	 * @param end
	 */
	private void sort(List<E> a, int start, int end) {
		if (end - start <= 1)
			return;

		int mid = (end + start) / 2;

		sort(a, start, mid);
		sort(a, mid, end);

		// start mid end
		// a: [ | sorted | sorted | ]

		merge(a, start, mid, end);
	}

	/**
	 * Precondition: start mid end a: [ | sorted | sorted | ]
	 * 
	 * Postcondition: start end a: [ | sorted | ]
	 * 
	 */
	private void merge(List<E> a, int start, int mid, int end) {
		List<E> result = new ArrayList<E>();

		// invariant: result is sorted
		// start i mid j end
		// a: [ | in result | sorted, >= result | in result | sorted, >= result | ]

		int i = start;
		int j = mid;

		while (i != mid || j != end) {
			if (i == mid) {
				result.add(a.get(j));
				j++;
			} else if (j == end) {
				result.add(a.get(i));
				i++;
			} else if (compare(a, i, j) < 0) {
				result.add(a.get(i));
				i++;
			} else {
				result.add(a.get(j));
				j++;
			}
		}

		// a: [ | in result | in result | ]

		// copy result back into a
		for (int k = 0; k < result.size(); k++) {
			a.set(start + k, result.get(k));
		}
	}

	

	public static class Tests extends Sorter.Tests {
		public MergeSorter<Integer> sorter() {
			return new MergeSorter<Integer>(Comparator.naturalOrder());
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