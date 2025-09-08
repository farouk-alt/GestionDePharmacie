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

import gestion_de_pharmacie.exception.NoSuchMouvementStockException;

import gestion_de_pharmacie.model.MouvementStock;

import gestion_de_pharmacie.service.MouvementStockLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.MouvementStockPersistence;
import gestion_de_pharmacie.service.persistence.MouvementStockUtil;

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
public class MouvementStockPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = MouvementStockUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<MouvementStock> iterator = _mouvementStocks.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MouvementStock mouvementStock = _persistence.create(pk);

		Assert.assertNotNull(mouvementStock);

		Assert.assertEquals(mouvementStock.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		MouvementStock newMouvementStock = addMouvementStock();

		_persistence.remove(newMouvementStock);

		MouvementStock existingMouvementStock = _persistence.fetchByPrimaryKey(
			newMouvementStock.getPrimaryKey());

		Assert.assertNull(existingMouvementStock);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMouvementStock();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MouvementStock newMouvementStock = _persistence.create(pk);

		newMouvementStock.setIdStock(RandomTestUtil.nextLong());

		newMouvementStock.setTypeMouvement(RandomTestUtil.randomString());

		newMouvementStock.setQuantite(RandomTestUtil.nextInt());

		newMouvementStock.setDateMouvement(RandomTestUtil.nextDate());

		_mouvementStocks.add(_persistence.update(newMouvementStock));

		MouvementStock existingMouvementStock = _persistence.findByPrimaryKey(
			newMouvementStock.getPrimaryKey());

		Assert.assertEquals(
			existingMouvementStock.getIdMouvement(),
			newMouvementStock.getIdMouvement());
		Assert.assertEquals(
			existingMouvementStock.getIdStock(),
			newMouvementStock.getIdStock());
		Assert.assertEquals(
			existingMouvementStock.getTypeMouvement(),
			newMouvementStock.getTypeMouvement());
		Assert.assertEquals(
			existingMouvementStock.getQuantite(),
			newMouvementStock.getQuantite());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMouvementStock.getDateMouvement()),
			Time.getShortTimestamp(newMouvementStock.getDateMouvement()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		MouvementStock newMouvementStock = addMouvementStock();

		MouvementStock existingMouvementStock = _persistence.findByPrimaryKey(
			newMouvementStock.getPrimaryKey());

		Assert.assertEquals(existingMouvementStock, newMouvementStock);
	}

	@Test(expected = NoSuchMouvementStockException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<MouvementStock> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_MouvementStock", "idMouvement", true, "idStock", true,
			"typeMouvement", true, "quantite", true, "dateMouvement", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		MouvementStock newMouvementStock = addMouvementStock();

		MouvementStock existingMouvementStock = _persistence.fetchByPrimaryKey(
			newMouvementStock.getPrimaryKey());

		Assert.assertEquals(existingMouvementStock, newMouvementStock);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MouvementStock missingMouvementStock = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingMouvementStock);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		MouvementStock newMouvementStock1 = addMouvementStock();
		MouvementStock newMouvementStock2 = addMouvementStock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMouvementStock1.getPrimaryKey());
		primaryKeys.add(newMouvementStock2.getPrimaryKey());

		Map<Serializable, MouvementStock> mouvementStocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, mouvementStocks.size());
		Assert.assertEquals(
			newMouvementStock1,
			mouvementStocks.get(newMouvementStock1.getPrimaryKey()));
		Assert.assertEquals(
			newMouvementStock2,
			mouvementStocks.get(newMouvementStock2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, MouvementStock> mouvementStocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mouvementStocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		MouvementStock newMouvementStock = addMouvementStock();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMouvementStock.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, MouvementStock> mouvementStocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mouvementStocks.size());
		Assert.assertEquals(
			newMouvementStock,
			mouvementStocks.get(newMouvementStock.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, MouvementStock> mouvementStocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(mouvementStocks.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		MouvementStock newMouvementStock = addMouvementStock();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMouvementStock.getPrimaryKey());

		Map<Serializable, MouvementStock> mouvementStocks =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, mouvementStocks.size());
		Assert.assertEquals(
			newMouvementStock,
			mouvementStocks.get(newMouvementStock.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			MouvementStockLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MouvementStock>() {

				@Override
				public void performAction(MouvementStock mouvementStock) {
					Assert.assertNotNull(mouvementStock);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		MouvementStock newMouvementStock = addMouvementStock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MouvementStock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idMouvement", newMouvementStock.getIdMouvement()));

		List<MouvementStock> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		MouvementStock existingMouvementStock = result.get(0);

		Assert.assertEquals(existingMouvementStock, newMouvementStock);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MouvementStock.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idMouvement", RandomTestUtil.nextLong()));

		List<MouvementStock> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		MouvementStock newMouvementStock = addMouvementStock();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MouvementStock.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idMouvement"));

		Object newIdMouvement = newMouvementStock.getIdMouvement();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idMouvement", new Object[] {newIdMouvement}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdMouvement = result.get(0);

		Assert.assertEquals(existingIdMouvement, newIdMouvement);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MouvementStock.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idMouvement"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idMouvement", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected MouvementStock addMouvementStock() throws Exception {
		long pk = RandomTestUtil.nextLong();

		MouvementStock mouvementStock = _persistence.create(pk);

		mouvementStock.setIdStock(RandomTestUtil.nextLong());

		mouvementStock.setTypeMouvement(RandomTestUtil.randomString());

		mouvementStock.setQuantite(RandomTestUtil.nextInt());

		mouvementStock.setDateMouvement(RandomTestUtil.nextDate());

		_mouvementStocks.add(_persistence.update(mouvementStock));

		return mouvementStock;
	}

	private List<MouvementStock> _mouvementStocks =
		new ArrayList<MouvementStock>();
	private MouvementStockPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}