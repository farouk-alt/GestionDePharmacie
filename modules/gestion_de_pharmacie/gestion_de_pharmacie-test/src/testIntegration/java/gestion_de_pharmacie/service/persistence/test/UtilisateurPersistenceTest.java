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
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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

import gestion_de_pharmacie.exception.NoSuchUtilisateurException;

import gestion_de_pharmacie.model.Utilisateur;

import gestion_de_pharmacie.service.UtilisateurLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.UtilisateurPersistence;
import gestion_de_pharmacie.service.persistence.UtilisateurUtil;

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
public class UtilisateurPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = UtilisateurUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Utilisateur> iterator = _utilisateurs.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Utilisateur utilisateur = _persistence.create(pk);

		Assert.assertNotNull(utilisateur);

		Assert.assertEquals(utilisateur.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		_persistence.remove(newUtilisateur);

		Utilisateur existingUtilisateur = _persistence.fetchByPrimaryKey(
			newUtilisateur.getPrimaryKey());

		Assert.assertNull(existingUtilisateur);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addUtilisateur();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Utilisateur newUtilisateur = _persistence.create(pk);

		newUtilisateur.setNom(RandomTestUtil.randomString());

		newUtilisateur.setPrenom(RandomTestUtil.randomString());

		newUtilisateur.setEmail(RandomTestUtil.randomString());

		newUtilisateur.setMotDePasse(RandomTestUtil.randomString());

		newUtilisateur.setRole(RandomTestUtil.randomString());

		newUtilisateur.setDateCreation(RandomTestUtil.nextDate());

		newUtilisateur.setLastLogin(RandomTestUtil.nextDate());

		_utilisateurs.add(_persistence.update(newUtilisateur));

		Utilisateur existingUtilisateur = _persistence.findByPrimaryKey(
			newUtilisateur.getPrimaryKey());

		Assert.assertEquals(
			existingUtilisateur.getIdUtilisateur(),
			newUtilisateur.getIdUtilisateur());
		Assert.assertEquals(
			existingUtilisateur.getNom(), newUtilisateur.getNom());
		Assert.assertEquals(
			existingUtilisateur.getPrenom(), newUtilisateur.getPrenom());
		Assert.assertEquals(
			existingUtilisateur.getEmail(), newUtilisateur.getEmail());
		Assert.assertEquals(
			existingUtilisateur.getMotDePasse(),
			newUtilisateur.getMotDePasse());
		Assert.assertEquals(
			existingUtilisateur.getRole(), newUtilisateur.getRole());
		Assert.assertEquals(
			Time.getShortTimestamp(existingUtilisateur.getDateCreation()),
			Time.getShortTimestamp(newUtilisateur.getDateCreation()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingUtilisateur.getLastLogin()),
			Time.getShortTimestamp(newUtilisateur.getLastLogin()));
	}

	@Test
	public void testCountByEmail() throws Exception {
		_persistence.countByEmail("");

		_persistence.countByEmail("null");

		_persistence.countByEmail((String)null);
	}

	@Test
	public void testCountByRole() throws Exception {
		_persistence.countByRole("");

		_persistence.countByRole("null");

		_persistence.countByRole((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		Utilisateur existingUtilisateur = _persistence.findByPrimaryKey(
			newUtilisateur.getPrimaryKey());

		Assert.assertEquals(existingUtilisateur, newUtilisateur);
	}

	@Test(expected = NoSuchUtilisateurException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Utilisateur> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Utilisateur", "idUtilisateur", true, "nom", true, "prenom",
			true, "email", true, "motDePasse", true, "role", true,
			"dateCreation", true, "lastLogin", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		Utilisateur existingUtilisateur = _persistence.fetchByPrimaryKey(
			newUtilisateur.getPrimaryKey());

		Assert.assertEquals(existingUtilisateur, newUtilisateur);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Utilisateur missingUtilisateur = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingUtilisateur);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Utilisateur newUtilisateur1 = addUtilisateur();
		Utilisateur newUtilisateur2 = addUtilisateur();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUtilisateur1.getPrimaryKey());
		primaryKeys.add(newUtilisateur2.getPrimaryKey());

		Map<Serializable, Utilisateur> utilisateurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, utilisateurs.size());
		Assert.assertEquals(
			newUtilisateur1, utilisateurs.get(newUtilisateur1.getPrimaryKey()));
		Assert.assertEquals(
			newUtilisateur2, utilisateurs.get(newUtilisateur2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Utilisateur> utilisateurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(utilisateurs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Utilisateur newUtilisateur = addUtilisateur();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUtilisateur.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Utilisateur> utilisateurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, utilisateurs.size());
		Assert.assertEquals(
			newUtilisateur, utilisateurs.get(newUtilisateur.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Utilisateur> utilisateurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(utilisateurs.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newUtilisateur.getPrimaryKey());

		Map<Serializable, Utilisateur> utilisateurs =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, utilisateurs.size());
		Assert.assertEquals(
			newUtilisateur, utilisateurs.get(newUtilisateur.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			UtilisateurLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Utilisateur>() {

				@Override
				public void performAction(Utilisateur utilisateur) {
					Assert.assertNotNull(utilisateur);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Utilisateur.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idUtilisateur", newUtilisateur.getIdUtilisateur()));

		List<Utilisateur> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Utilisateur existingUtilisateur = result.get(0);

		Assert.assertEquals(existingUtilisateur, newUtilisateur);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Utilisateur.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idUtilisateur", RandomTestUtil.nextLong()));

		List<Utilisateur> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Utilisateur.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idUtilisateur"));

		Object newIdUtilisateur = newUtilisateur.getIdUtilisateur();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idUtilisateur", new Object[] {newIdUtilisateur}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdUtilisateur = result.get(0);

		Assert.assertEquals(existingIdUtilisateur, newIdUtilisateur);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Utilisateur.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idUtilisateur"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idUtilisateur", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Utilisateur newUtilisateur = addUtilisateur();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(newUtilisateur.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		Utilisateur newUtilisateur = addUtilisateur();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Utilisateur.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idUtilisateur", newUtilisateur.getIdUtilisateur()));

		List<Utilisateur> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(Utilisateur utilisateur) {
		Assert.assertEquals(
			utilisateur.getEmail(),
			ReflectionTestUtil.invoke(
				utilisateur, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "email"));
	}

	protected Utilisateur addUtilisateur() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Utilisateur utilisateur = _persistence.create(pk);

		utilisateur.setNom(RandomTestUtil.randomString());

		utilisateur.setPrenom(RandomTestUtil.randomString());

		utilisateur.setEmail(RandomTestUtil.randomString());

		utilisateur.setMotDePasse(RandomTestUtil.randomString());

		utilisateur.setRole(RandomTestUtil.randomString());

		utilisateur.setDateCreation(RandomTestUtil.nextDate());

		utilisateur.setLastLogin(RandomTestUtil.nextDate());

		_utilisateurs.add(_persistence.update(utilisateur));

		return utilisateur;
	}

	private List<Utilisateur> _utilisateurs = new ArrayList<Utilisateur>();
	private UtilisateurPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}