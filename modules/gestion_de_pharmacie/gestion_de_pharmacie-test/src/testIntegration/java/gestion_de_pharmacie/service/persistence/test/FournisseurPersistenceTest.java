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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import gestion_de_pharmacie.exception.NoSuchFournisseurException;

import gestion_de_pharmacie.model.Fournisseur;

import gestion_de_pharmacie.service.FournisseurLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.FournisseurPersistence;
import gestion_de_pharmacie.service.persistence.FournisseurUtil;

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
public class FournisseurPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = FournisseurUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Fournisseur> iterator = _fournisseurs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Fournisseur fournisseur = _persistence.create(pk);

		Assert.assertNotNull(fournisseur);

		Assert.assertEquals(fournisseur.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Fournisseur newFournisseur = addFournisseur();

		_persistence.remove(newFournisseur);

		Fournisseur existingFournisseur = _persistence.fetchByPrimaryKey(
			newFournisseur.getPrimaryKey());

		Assert.assertNull(existingFournisseur);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addFournisseur();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Fournisseur newFournisseur = _persistence.create(pk);

		newFournisseur.setNom(RandomTestUtil.randomString());

		newFournisseur.setAdresse(RandomTestUtil.randomString());

		newFournisseur.setTelephone(RandomTestUtil.randomString());

		newFournisseur.setEmail(RandomTestUtil.randomString());

		_fournisseurs.add(_persistence.update(newFournisseur));

		Fournisseur existingFournisseur = _persistence.findByPrimaryKey(
			newFournisseur.getPrimaryKey());

		Assert.assertEquals(
			existingFournisseur.getIdFournisseur(),
			newFournisseur.getIdFournisseur());
		Assert.assertEquals(
			existingFournisseur.getNom(), newFournisseur.getNom());
		Assert.assertEquals(
			existingFournisseur.getAdresse(), newFournisseur.getAdresse());
		Assert.assertEquals(
			existingFournisseur.getTelephone(), newFournisseur.getTelephone());
		Assert.assertEquals(
			existingFournisseur.getEmail(), newFournisseur.getEmail());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Fournisseur newFournisseur = addFournisseur();

		Fournisseur existingFournisseur = _persistence.findByPrimaryKey(
			newFournisseur.getPrimaryKey());

		Assert.assertEquals(existingFournisseur, newFournisseur);
	}

	@Test(expected = NoSuchFournisseurException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Fournisseur> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Fournisseur", "idFournisseur", true, "nom", true, "adresse",
			true, "telephone", true, "email", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Fournisseur newFournisseur = addFournisseur();

		Fournisseur existingFournisseur = _persistence.fetchByPrimaryKey(
			newFournisseur.getPrimaryKey());

		Assert.assertEquals(existingFournisseur, newFournisseur);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Fournisseur missingFournisseur = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingFournisseur);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Fournisseur newFournisseur1 = addFournisseur();
		Fournisseur newFournisseur2 = addFournisseur();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFournisseur1.getPrimaryKey());
		primaryKeys.add(newFournisseur2.getPrimaryKey());

		Map<Serializable, Fournisseur> fournisseurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, fournisseurs.size());
		Assert.assertEquals(
			newFournisseur1, fournisseurs.get(newFournisseur1.getPrimaryKey()));
		Assert.assertEquals(
			newFournisseur2, fournisseurs.get(newFournisseur2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Fournisseur> fournisseurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fournisseurs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Fournisseur newFournisseur = addFournisseur();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFournisseur.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Fournisseur> fournisseurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fournisseurs.size());
		Assert.assertEquals(
			newFournisseur, fournisseurs.get(newFournisseur.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Fournisseur> fournisseurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(fournisseurs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Fournisseur newFournisseur = addFournisseur();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newFournisseur.getPrimaryKey());

		Map<Serializable, Fournisseur> fournisseurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, fournisseurs.size());
		Assert.assertEquals(
			newFournisseur, fournisseurs.get(newFournisseur.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			FournisseurLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Fournisseur>() {

				@Override
				public void performAction(Fournisseur fournisseur) {
					Assert.assertNotNull(fournisseur);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Fournisseur newFournisseur = addFournisseur();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Fournisseur.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idFournisseur", newFournisseur.getIdFournisseur()));

		List<Fournisseur> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Fournisseur existingFournisseur = result.get(0);

		Assert.assertEquals(existingFournisseur, newFournisseur);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Fournisseur.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idFournisseur", RandomTestUtil.nextLong()));

		List<Fournisseur> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Fournisseur newFournisseur = addFournisseur();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Fournisseur.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idFournisseur"));

		Object newIdFournisseur = newFournisseur.getIdFournisseur();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idFournisseur", new Object[] {newIdFournisseur}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdFournisseur = result.get(0);

		Assert.assertEquals(existingIdFournisseur, newIdFournisseur);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Fournisseur.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idFournisseur"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idFournisseur", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Fournisseur addFournisseur() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Fournisseur fournisseur = _persistence.create(pk);

		fournisseur.setNom(RandomTestUtil.randomString());

		fournisseur.setAdresse(RandomTestUtil.randomString());

		fournisseur.setTelephone(RandomTestUtil.randomString());

		fournisseur.setEmail(RandomTestUtil.randomString());

		_fournisseurs.add(_persistence.update(fournisseur));

		return fournisseur;
	}

	private List<Fournisseur> _fournisseurs = new ArrayList<Fournisseur>();
	private FournisseurPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}