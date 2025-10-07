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
import com.liferay.portal.kernel.test.AssertUtils;
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

import gestion_de_pharmacie.exception.NoSuchVenteException;

import gestion_de_pharmacie.model.Vente;

import gestion_de_pharmacie.service.VenteLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.VentePersistence;
import gestion_de_pharmacie.service.persistence.VenteUtil;

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
public class VentePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = VenteUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Vente> iterator = _ventes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Vente vente = _persistence.create(pk);

		Assert.assertNotNull(vente);

		Assert.assertEquals(vente.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Vente newVente = addVente();

		_persistence.remove(newVente);

		Vente existingVente = _persistence.fetchByPrimaryKey(
			newVente.getPrimaryKey());

		Assert.assertNull(existingVente);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVente();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Vente newVente = _persistence.create(pk);

		newVente.setIdUtilisateur(RandomTestUtil.nextLong());

		newVente.setDateVente(RandomTestUtil.nextDate());

		newVente.setMontantTotal(RandomTestUtil.nextDouble());

		_ventes.add(_persistence.update(newVente));

		Vente existingVente = _persistence.findByPrimaryKey(
			newVente.getPrimaryKey());

		Assert.assertEquals(existingVente.getIdVente(), newVente.getIdVente());
		Assert.assertEquals(
			existingVente.getIdUtilisateur(), newVente.getIdUtilisateur());
		Assert.assertEquals(
			Time.getShortTimestamp(existingVente.getDateVente()),
			Time.getShortTimestamp(newVente.getDateVente()));
		AssertUtils.assertEquals(
			existingVente.getMontantTotal(), newVente.getMontantTotal());
	}

	@Test
	public void testCountByUser() throws Exception {
		_persistence.countByUser(RandomTestUtil.nextLong());

		_persistence.countByUser(0L);
	}

	@Test
	public void testCountByDate() throws Exception {
		_persistence.countByDate(RandomTestUtil.nextDate());

		_persistence.countByDate(RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Vente newVente = addVente();

		Vente existingVente = _persistence.findByPrimaryKey(
			newVente.getPrimaryKey());

		Assert.assertEquals(existingVente, newVente);
	}

	@Test(expected = NoSuchVenteException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Vente> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Vente", "idVente", true, "idUtilisateur", true, "dateVente",
			true, "montantTotal", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Vente newVente = addVente();

		Vente existingVente = _persistence.fetchByPrimaryKey(
			newVente.getPrimaryKey());

		Assert.assertEquals(existingVente, newVente);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Vente missingVente = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVente);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Vente newVente1 = addVente();
		Vente newVente2 = addVente();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVente1.getPrimaryKey());
		primaryKeys.add(newVente2.getPrimaryKey());

		Map<Serializable, Vente> ventes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, ventes.size());
		Assert.assertEquals(newVente1, ventes.get(newVente1.getPrimaryKey()));
		Assert.assertEquals(newVente2, ventes.get(newVente2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Vente> ventes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(ventes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Vente newVente = addVente();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVente.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Vente> ventes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, ventes.size());
		Assert.assertEquals(newVente, ventes.get(newVente.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Vente> ventes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(ventes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Vente newVente = addVente();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVente.getPrimaryKey());

		Map<Serializable, Vente> ventes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, ventes.size());
		Assert.assertEquals(newVente, ventes.get(newVente.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			VenteLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Vente>() {

				@Override
				public void performAction(Vente vente) {
					Assert.assertNotNull(vente);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Vente newVente = addVente();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Vente.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("idVente", newVente.getIdVente()));

		List<Vente> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Vente existingVente = result.get(0);

		Assert.assertEquals(existingVente, newVente);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Vente.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("idVente", RandomTestUtil.nextLong()));

		List<Vente> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Vente newVente = addVente();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Vente.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idVente"));

		Object newIdVente = newVente.getIdVente();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("idVente", new Object[] {newIdVente}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdVente = result.get(0);

		Assert.assertEquals(existingIdVente, newIdVente);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Vente.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idVente"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idVente", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Vente addVente() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Vente vente = _persistence.create(pk);

		vente.setIdUtilisateur(RandomTestUtil.nextLong());

		vente.setDateVente(RandomTestUtil.nextDate());

		vente.setMontantTotal(RandomTestUtil.nextDouble());

		_ventes.add(_persistence.update(vente));

		return vente;
	}

	private List<Vente> _ventes = new ArrayList<Vente>();
	private VentePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}