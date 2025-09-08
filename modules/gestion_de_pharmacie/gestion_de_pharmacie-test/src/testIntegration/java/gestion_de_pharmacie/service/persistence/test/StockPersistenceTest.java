/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package gestion_de_pharmacie.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import gestion_de_pharmacie.exception.NoSuchStockException;

import gestion_de_pharmacie.model.Stock;

import gestion_de_pharmacie.service.StockLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.StockPersistence;
import gestion_de_pharmacie.service.persistence.StockUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class StockPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = StockUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Stock> iterator = _stocks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Stock stock = _persistence.create(pk);

		Assert.assertNotNull(stock);

		Assert.assertEquals(stock.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Stock newStock = addStock();

		_persistence.remove(newStock);

		Stock existingStock = _persistence.fetchByPrimaryKey(
			newStock.getPrimaryKey());

		Assert.assertNull(existingStock);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addStock();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Stock newStock = _persistence.create(pk);

		newStock.setIdMedicament(RandomTestUtil.nextLong());

		newStock.setQuantiteDisponible(RandomTestUtil.nextInt());

		newStock.setDateDerniereMaj(RandomTestUtil.nextDate());

		_stocks.add(_persistence.update(newStock));

		Stock existingStock = _persistence.findByPrimaryKey(
			newStock.getPrimaryKey());

		Assert.assertEquals(existingStock.getIdStock(), newStock.getIdStock());
		Assert.assertEquals(
			existingStock.getIdMedicament(), newStock.getIdMedicament());
		Assert.assertEquals(
			existingStock.getQuantiteDisponible(),
			newStock.getQuantiteDisponible());
		Assert.assertEquals(
			Time.getShortTimestamp(existingStock.getDateDerniereMaj()),
			Time.getShortTimestamp(newStock.getDateDerniereMaj()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Stock newStock = addStock();

		Stock existingStock = _persistence.findByPrimaryKey(
			newStock.getPrimaryKey());

		Assert.assertEquals(existingStock, newStock);
	}

	@Test(expected = NoSuchStockException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Stock> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Stock", "idStock", true, "idMedicament", true,
			"quantiteDisponible", true, "dateDerniereMaj", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Stock newStock = addStock();

		Stock existingStock = _persistence.fetchByPrimaryKey(
			newStock.getPrimaryKey());

		Assert.assertEquals(existingStock, newStock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Stock missingStock = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingStock);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Stock newStock1 = addStock();
		Stock newStock2 = addStock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStock1.getPrimaryKey());
		primaryKeys.add(newStock2.getPrimaryKey());

		Map<Serializable, Stock> stocks = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, stocks.size());
		Assert.assertEquals(newStock1, stocks.get(newStock1.getPrimaryKey()));
		Assert.assertEquals(newStock2, stocks.get(newStock2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Stock> stocks = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(stocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Stock newStock = addStock();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStock.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Stock> stocks = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, stocks.size());
		Assert.assertEquals(newStock, stocks.get(newStock.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Stock> stocks = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(stocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Stock newStock = addStock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newStock.getPrimaryKey());

		Map<Serializable, Stock> stocks = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, stocks.size());
		Assert.assertEquals(newStock, stocks.get(newStock.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			StockLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Stock>() {

				@Override
				public void performAction(Stock stock) {
					Assert.assertNotNull(stock);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Stock newStock = addStock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Stock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("idStock", newStock.getIdStock()));

		List<Stock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Stock existingStock = result.get(0);

		Assert.assertEquals(existingStock, newStock);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Stock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("idStock", RandomTestUtil.nextLong()));

		List<Stock> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Stock newStock = addStock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Stock.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idStock"));

		Object newIdStock = newStock.getIdStock();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("idStock", new Object[] {newIdStock}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdStock = result.get(0);

		Assert.assertEquals(existingIdStock, newIdStock);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Stock.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idStock"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idStock", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Stock addStock() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Stock stock = _persistence.create(pk);

		stock.setIdMedicament(RandomTestUtil.nextLong());

		stock.setQuantiteDisponible(RandomTestUtil.nextInt());

		stock.setDateDerniereMaj(RandomTestUtil.nextDate());

		_stocks.add(_persistence.update(stock));

		return stock;
	}

	private List<Stock> _stocks = new ArrayList<Stock>();
	private StockPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}