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

import gestion_de_pharmacie.exception.NoSuchCommandeException;

import gestion_de_pharmacie.model.Commande;

import gestion_de_pharmacie.service.CommandeLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.CommandePersistence;
import gestion_de_pharmacie.service.persistence.CommandeUtil;

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
public class CommandePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = CommandeUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Commande> iterator = _commandes.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Commande commande = _persistence.create(pk);

		Assert.assertNotNull(commande);

		Assert.assertEquals(commande.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Commande newCommande = addCommande();

		_persistence.remove(newCommande);

		Commande existingCommande = _persistence.fetchByPrimaryKey(
			newCommande.getPrimaryKey());

		Assert.assertNull(existingCommande);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommande();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Commande newCommande = _persistence.create(pk);

		newCommande.setIdUtilisateur(RandomTestUtil.nextLong());

		newCommande.setDateCommande(RandomTestUtil.nextDate());

		newCommande.setStatut(RandomTestUtil.randomString());

		newCommande.setMontantTotal(RandomTestUtil.nextDouble());

		_commandes.add(_persistence.update(newCommande));

		Commande existingCommande = _persistence.findByPrimaryKey(
			newCommande.getPrimaryKey());

		Assert.assertEquals(
			existingCommande.getIdCommande(), newCommande.getIdCommande());
		Assert.assertEquals(
			existingCommande.getIdUtilisateur(),
			newCommande.getIdUtilisateur());
		Assert.assertEquals(
			Time.getShortTimestamp(existingCommande.getDateCommande()),
			Time.getShortTimestamp(newCommande.getDateCommande()));
		Assert.assertEquals(
			existingCommande.getStatut(), newCommande.getStatut());
		AssertUtils.assertEquals(
			existingCommande.getMontantTotal(), newCommande.getMontantTotal());
	}

	@Test
	public void testCountByIdFournisseur() throws Exception {
		_persistence.countByIdFournisseur(RandomTestUtil.nextLong());

		_persistence.countByIdFournisseur(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Commande newCommande = addCommande();

		Commande existingCommande = _persistence.findByPrimaryKey(
			newCommande.getPrimaryKey());

		Assert.assertEquals(existingCommande, newCommande);
	}

	@Test(expected = NoSuchCommandeException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Commande> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Commande", "idCommande", true, "idUtilisateur", true,
			"dateCommande", true, "statut", true, "montantTotal", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Commande newCommande = addCommande();

		Commande existingCommande = _persistence.fetchByPrimaryKey(
			newCommande.getPrimaryKey());

		Assert.assertEquals(existingCommande, newCommande);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Commande missingCommande = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommande);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Commande newCommande1 = addCommande();
		Commande newCommande2 = addCommande();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommande1.getPrimaryKey());
		primaryKeys.add(newCommande2.getPrimaryKey());

		Map<Serializable, Commande> commandes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, commandes.size());
		Assert.assertEquals(
			newCommande1, commandes.get(newCommande1.getPrimaryKey()));
		Assert.assertEquals(
			newCommande2, commandes.get(newCommande2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Commande> commandes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(commandes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Commande newCommande = addCommande();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommande.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Commande> commandes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, commandes.size());
		Assert.assertEquals(
			newCommande, commandes.get(newCommande.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Commande> commandes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(commandes.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Commande newCommande = addCommande();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommande.getPrimaryKey());

		Map<Serializable, Commande> commandes = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, commandes.size());
		Assert.assertEquals(
			newCommande, commandes.get(newCommande.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommandeLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Commande>() {

				@Override
				public void performAction(Commande commande) {
					Assert.assertNotNull(commande);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Commande newCommande = addCommande();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Commande.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idCommande", newCommande.getIdCommande()));

		List<Commande> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Commande existingCommande = result.get(0);

		Assert.assertEquals(existingCommande, newCommande);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Commande.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idCommande", RandomTestUtil.nextLong()));

		List<Commande> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Commande newCommande = addCommande();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Commande.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idCommande"));

		Object newIdCommande = newCommande.getIdCommande();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idCommande", new Object[] {newIdCommande}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdCommande = result.get(0);

		Assert.assertEquals(existingIdCommande, newIdCommande);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Commande.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idCommande"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idCommande", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Commande addCommande() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Commande commande = _persistence.create(pk);

		commande.setIdUtilisateur(RandomTestUtil.nextLong());

		commande.setDateCommande(RandomTestUtil.nextDate());

		commande.setStatut(RandomTestUtil.randomString());

		commande.setMontantTotal(RandomTestUtil.nextDouble());

		_commandes.add(_persistence.update(commande));

		return commande;
	}

	private List<Commande> _commandes = new ArrayList<Commande>();
	private CommandePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}