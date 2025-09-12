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

import gestion_de_pharmacie.exception.NoSuchMedicamentException;

import gestion_de_pharmacie.model.Medicament;

import gestion_de_pharmacie.service.MedicamentLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.MedicamentPersistence;
import gestion_de_pharmacie.service.persistence.MedicamentUtil;

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
public class MedicamentPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = MedicamentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Medicament> iterator = _medicaments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Medicament medicament = _persistence.create(pk);

		Assert.assertNotNull(medicament);

		Assert.assertEquals(medicament.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Medicament newMedicament = addMedicament();

		_persistence.remove(newMedicament);

		Medicament existingMedicament = _persistence.fetchByPrimaryKey(
			newMedicament.getPrimaryKey());

		Assert.assertNull(existingMedicament);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMedicament();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Medicament newMedicament = _persistence.create(pk);

		newMedicament.setCode(RandomTestUtil.randomString());

		newMedicament.setCodeBarre(RandomTestUtil.randomString());

		newMedicament.setNom(RandomTestUtil.randomString());

		newMedicament.setDescription(RandomTestUtil.randomString());

		newMedicament.setCategorie(RandomTestUtil.randomString());

		newMedicament.setPrixUnitaire(RandomTestUtil.nextDouble());

		newMedicament.setSeuilMinimum(RandomTestUtil.nextInt());

		newMedicament.setDateAjout(RandomTestUtil.nextDate());

		_medicaments.add(_persistence.update(newMedicament));

		Medicament existingMedicament = _persistence.findByPrimaryKey(
			newMedicament.getPrimaryKey());

		Assert.assertEquals(
			existingMedicament.getIdMedicament(),
			newMedicament.getIdMedicament());
		Assert.assertEquals(
			existingMedicament.getCode(), newMedicament.getCode());
		Assert.assertEquals(
			existingMedicament.getCodeBarre(), newMedicament.getCodeBarre());
		Assert.assertEquals(
			existingMedicament.getNom(), newMedicament.getNom());
		Assert.assertEquals(
			existingMedicament.getDescription(),
			newMedicament.getDescription());
		Assert.assertEquals(
			existingMedicament.getCategorie(), newMedicament.getCategorie());
		AssertUtils.assertEquals(
			existingMedicament.getPrixUnitaire(),
			newMedicament.getPrixUnitaire());
		Assert.assertEquals(
			existingMedicament.getSeuilMinimum(),
			newMedicament.getSeuilMinimum());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMedicament.getDateAjout()),
			Time.getShortTimestamp(newMedicament.getDateAjout()));
	}

	@Test
	public void testCountByCode() throws Exception {
		_persistence.countByCode("");

		_persistence.countByCode("null");

		_persistence.countByCode((String)null);
	}

	@Test
	public void testCountByCodeBarre() throws Exception {
		_persistence.countByCodeBarre("");

		_persistence.countByCodeBarre("null");

		_persistence.countByCodeBarre((String)null);
	}

	@Test
	public void testCountByNom() throws Exception {
		_persistence.countByNom("");

		_persistence.countByNom("null");

		_persistence.countByNom((String)null);
	}

	@Test
	public void testCountByCategorie() throws Exception {
		_persistence.countByCategorie("");

		_persistence.countByCategorie("null");

		_persistence.countByCategorie((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Medicament newMedicament = addMedicament();

		Medicament existingMedicament = _persistence.findByPrimaryKey(
			newMedicament.getPrimaryKey());

		Assert.assertEquals(existingMedicament, newMedicament);
	}

	@Test(expected = NoSuchMedicamentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Medicament> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Medicament", "idMedicament", true, "code", true,
			"codeBarre", true, "nom", true, "description", true, "categorie",
			true, "prixUnitaire", true, "seuilMinimum", true, "dateAjout",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Medicament newMedicament = addMedicament();

		Medicament existingMedicament = _persistence.fetchByPrimaryKey(
			newMedicament.getPrimaryKey());

		Assert.assertEquals(existingMedicament, newMedicament);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Medicament missingMedicament = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMedicament);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Medicament newMedicament1 = addMedicament();
		Medicament newMedicament2 = addMedicament();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMedicament1.getPrimaryKey());
		primaryKeys.add(newMedicament2.getPrimaryKey());

		Map<Serializable, Medicament> medicaments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, medicaments.size());
		Assert.assertEquals(
			newMedicament1, medicaments.get(newMedicament1.getPrimaryKey()));
		Assert.assertEquals(
			newMedicament2, medicaments.get(newMedicament2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Medicament> medicaments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(medicaments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Medicament newMedicament = addMedicament();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMedicament.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Medicament> medicaments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, medicaments.size());
		Assert.assertEquals(
			newMedicament, medicaments.get(newMedicament.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Medicament> medicaments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(medicaments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Medicament newMedicament = addMedicament();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMedicament.getPrimaryKey());

		Map<Serializable, Medicament> medicaments =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, medicaments.size());
		Assert.assertEquals(
			newMedicament, medicaments.get(newMedicament.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			MedicamentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Medicament>() {

				@Override
				public void performAction(Medicament medicament) {
					Assert.assertNotNull(medicament);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Medicament newMedicament = addMedicament();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Medicament.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idMedicament", newMedicament.getIdMedicament()));

		List<Medicament> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Medicament existingMedicament = result.get(0);

		Assert.assertEquals(existingMedicament, newMedicament);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Medicament.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idMedicament", RandomTestUtil.nextLong()));

		List<Medicament> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Medicament newMedicament = addMedicament();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Medicament.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idMedicament"));

		Object newIdMedicament = newMedicament.getIdMedicament();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idMedicament", new Object[] {newIdMedicament}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdMedicament = result.get(0);

		Assert.assertEquals(existingIdMedicament, newIdMedicament);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Medicament.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idMedicament"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idMedicament", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Medicament addMedicament() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Medicament medicament = _persistence.create(pk);

		medicament.setCode(RandomTestUtil.randomString());

		medicament.setCodeBarre(RandomTestUtil.randomString());

		medicament.setNom(RandomTestUtil.randomString());

		medicament.setDescription(RandomTestUtil.randomString());

		medicament.setCategorie(RandomTestUtil.randomString());

		medicament.setPrixUnitaire(RandomTestUtil.nextDouble());

		medicament.setSeuilMinimum(RandomTestUtil.nextInt());

		medicament.setDateAjout(RandomTestUtil.nextDate());

		_medicaments.add(_persistence.update(medicament));

		return medicament;
	}

	private List<Medicament> _medicaments = new ArrayList<Medicament>();
	private MedicamentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}