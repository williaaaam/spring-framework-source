package com.xjz.springframework.test;

import com.xjz.springframework.domain.Person;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author Williami
 * @description
 * @date 2021/9/10
 */
public class ComparatorsTest {

	// 第一种写法
	private static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
		if (c1 == c2) {
			return 0;
		} else if (c1 == null) {
			return nullGreater ? 1 : -1;
		} else if (c2 == null) {
			return nullGreater ? -1 : 1;
		}
		return c1.compareTo(c2);
	}

	/**
	 * <p>Null safe comparison of Comparables.
	 * {@code null} is assumed to be less than a non-{@code null} value.</p>
	 *
	 * @param <T> type of the values processed by this method
	 * @param c1  the first comparable, may be null
	 * @param c2  the second comparable, may be null
	 * @return a negative value if c1 &lt; c2, zero if c1 = c2
	 * and a positive value if c1 &gt; c2
	 */
	public static <T extends Comparable<? super T>> int compare(final T c1, final T c2) {
		return compare(c1, c2, false);
	}


	// 第二种写法
	static class Comparators {
		private Comparators() {
			throw new AssertionError("no instances");
		}

		/**
		 * Compares {@link Comparable} objects in natural order.
		 *
		 * @see Comparable
		 */
		enum NaturalOrderComparator implements Comparator<Comparable<Object>> {
			INSTANCE;

			@Override
			public int compare(Comparable<Object> c1, Comparable<Object> c2) {
				return c1.compareTo(c2);
			}

			@Override
			public Comparator<Comparable<Object>> reversed() {
				return Comparator.reverseOrder();
			}
		}

		/**
		 * Null-friendly comparators
		 */
		static final class NullComparator<T> implements Comparator<T>, Serializable {
			private static final long serialVersionUID = -7569533591570686392L;
			private final boolean nullFirst;
			// if null, non-null Ts are considered equal
			private final Comparator<T> real;

			@SuppressWarnings("unchecked")
			NullComparator(boolean nullFirst, Comparator<? super T> real) {
				this.nullFirst = nullFirst;
				this.real = (Comparator<T>) real;
			}


			@Override
			public int compare(T a, T b) {
				if (a == null) {
					return (b == null) ? 0 : (nullFirst ? -1 : 1);
				} else if (b == null) {
					return nullFirst ? 1 : -1;
				} else {
					return (real == null) ? 0 : real.compare(a, b);
				}
			}

			@Override
			public Comparator<T> thenComparing(Comparator<? super T> other) {
				Objects.requireNonNull(other);
				return new Comparators.NullComparator<>(nullFirst, real == null ? other : real.thenComparing(other));
			}

			@Override
			public Comparator<T> reversed() {
				return new Comparators.NullComparator<>(!nullFirst, real == null ? null : real.reversed());
			}
		}
	}


	// 第三种写法
	public static int nullSafeStringComparator(final String one, final String two) {
		if (one == null ^ two == null) {
			return (one == null) ? -1 : 1;
		}

		if (one == null && two == null) {
			return 0;
		}

		return one.compareToIgnoreCase(two);
	}


	class Person implements Comparable<Person> {

		String name;
		int age;

		@Override
		public int compareTo(@NotNull Person o) {
			return compare(name, o.name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person{" +
					"name='" + name + '\'' +
					", age=" + age +
					'}';
		}
	}

	class User extends Person {
	}


	@Test
	public void sort() {
		User user = new User();
		user.setAge(2);
		user.setName("admin");

		User user2 = new User();
		user2.setAge(10);
		user2.setName("Scott");

		User user3 = new User();
		user3.setAge(100);
		user3.setName("Jimmy");

		Arrays.asList(user, user2, user3).stream().sorted().forEach(System.out::println);
	}

}
