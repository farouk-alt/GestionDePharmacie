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
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import gestion_de_pharmacie.exception.NoSuchCommandeDetailException;

import gestion_de_pharmacie.model.CommandeDetail;

import gestion_de_pharmacie.service.CommandeDetailLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.CommandeDetailPersistence;
import gestion_de_pharmacie.service.persistence.CommandeDetailUtil;

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
public class CommandeDetailPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = CommandeDetailUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommandeDetail> iterator = _commandeDetails.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommandeDetail commandeDetail = _persistence.create(pk);

		Assert.assertNotNull(commandeDetail);

		Assert.assertEquals(commandeDetail.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommandeDetail newCommandeDetail = addCommandeDetail();

		_persistence.remove(newCommandeDetail);

		CommandeDetail existingCommandeDetail = _persistence.fetchByPrimaryKey(
			newCommandeDetail.getPrimaryKey());

		Assert.assertNull(existingCommandeDetail);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommandeDetail();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommandeDetail newCommandeDetail = _persistence.create(pk);

		newCommandeDetail.setIdCommande(RandomTestUtil.nextLong());

		newCommandeDetail.setIdMedicament(RandomTestUtil.nextLong());

		newCommandeDetail.setQuantite(RandomTestUtil.nextInt());

		newCommandeDetail.setPrixUnitaire(RandomTestUtil.nextDouble());

		_commandeDetails.add(_persistence.update(newCommandeDetail));

		CommandeDetail existingCommandeDetail = _persistence.findByPrimaryKey(
			newCommandeDetail.getPrimaryKey());

		Assert.assertEquals(
			existingCommandeDetail.getIdDetail(),
			newCommandeDetail.getIdDetail());
		Assert.assertEquals(
			existingCommandeDetail.getIdCommande(),
			newCommandeDetail.getIdCommande());
		Assert.assertEquals(
			existingCommandeDetail.getIdMedicament(),
			newCommandeDetail.getIdMedicament());
		Assert.assertEquals(
			existingCommandeDetail.getQuantite(),
			newCommandeDetail.getQuantite());
		AssertUtils.assertEquals(
			existingCommandeDetail.getPrixUnitaire(),
			newCommandeDetail.getPrixUnitaire());
	}

	@Test
	public void testCountByIdCommande() throws Exception {
		_persistence.countByIdCommande(RandomTestUtil.nextLong());

		_persistence.countByIdCommande(0L);
	}

	@Test
	public void testCountByIdMedicament() throws Exception {
		_persistence.countByIdMedicament(RandomTestUtil.nextLong());

		_persistence.countByIdMedicament(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommandeDetail newCommandeDetail = addCommandeDetail();

		CommandeDetail existingCommandeDetail = _persistence.findByPrimaryKey(
			newCommandeDetail.getPrimaryKey());

		Assert.assertEquals(existingCommandeDetail, newCommandeDetail);
	}

	@Test(expected = NoSuchCommandeDetailException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommandeDetail> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_CommandeDetail", "idDetail", true, "idCommande", true,
			"idMedicament", true, "quantite", true, "prixUnitaire", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommandeDetail newCommandeDetail = addCommandeDetail();

		CommandeDetail existingCommandeDetail = _persistence.fetchByPrimaryKey(
			newCommandeDetail.getPrimaryKey());

		Assert.assertEquals(existingCommandeDetail, newCommandeDetail);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommandeDetail missingCommandeDetail = _persistence.fetchByPrimaryKey(
			pk);

		Assert.assertNull(missingCommandeDetail);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommandeDetail newCommandeDetail1 = addCommandeDetail();
		CommandeDetail newCommandeDetail2 = addCommandeDetail();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommandeDetail1.getPrimaryKey());
		primaryKeys.add(newCommandeDetail2.getPrimaryKey());

		Map<Serializable, CommandeDetail> commandeDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, commandeDetails.size());
		Assert.assertEquals(
			newCommandeDetail1,
			commandeDetails.get(newCommandeDetail1.getPrimaryKey()));
		Assert.assertEquals(
			newCommandeDetail2,
			commandeDetails.get(newCommandeDetail2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommandeDetail> commandeDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commandeDetails.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommandeDetail newCommandeDetail = addCommandeDetail();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommandeDetail.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommandeDetail> commandeDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commandeDetails.size());
		Assert.assertEquals(
			newCommandeDetail,
			commandeDetails.get(newCommandeDetail.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommandeDetail> commandeDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(commandeDetails.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommandeDetail newCommandeDetail = addCommandeDetail();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommandeDetail.getPrimaryKey());

		Map<Serializable, CommandeDetail> commandeDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, commandeDetails.size());
		Assert.assertEquals(
			newCommandeDetail,
			commandeDetails.get(newCommandeDetail.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommandeDetailLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<CommandeDetail>() {

				@Override
				public void performAction(CommandeDetail commandeDetail) {
					Assert.assertNotNull(commandeDetail);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommandeDetail newCommandeDetail = addCommandeDetail();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommandeDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idDetail", newCommandeDetail.getIdDetail()));

		List<CommandeDetail> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommandeDetail existingCommandeDetail = result.get(0);

		Assert.assertEquals(existingCommandeDetail, newCommandeDetail);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommandeDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("idDetail", RandomTestUtil.nextLong()));

		List<CommandeDetail> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommandeDetail newCommandeDetail = addCommandeDetail();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommandeDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idDetail"));

		Object newIdDetail = newCommandeDetail.getIdDetail();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("idDetail", new Object[] {newIdDetail}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdDetail = result.get(0);

		Assert.assertEquals(existingIdDetail, newIdDetail);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommandeDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idDetail"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idDetail", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CommandeDetail addCommandeDetail() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommandeDetail commandeDetail = _persistence.create(pk);

		commandeDetail.setIdCommande(RandomTestUtil.nextLong());

		commandeDetail.setIdMedicament(RandomTestUtil.nextLong());

		commandeDetail.setQuantite(RandomTestUtil.nextInt());

		commandeDetail.setPrixUnitaire(RandomTestUtil.nextDouble());

		_commandeDetails.add(_persistence.update(commandeDetail));

		return commandeDetail;
	}

	private List<CommandeDetail> _commandeDetails =
		new ArrayList<CommandeDetail>();
	private CommandeDetailPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}