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

import gestion_de_pharmacie.exception.NoSuchVenteDetailException;

import gestion_de_pharmacie.model.VenteDetail;

import gestion_de_pharmacie.service.VenteDetailLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.VenteDetailPersistence;
import gestion_de_pharmacie.service.persistence.VenteDetailUtil;

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
public class VenteDetailPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = VenteDetailUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<VenteDetail> iterator = _venteDetails.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VenteDetail venteDetail = _persistence.create(pk);

		Assert.assertNotNull(venteDetail);

		Assert.assertEquals(venteDetail.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		VenteDetail newVenteDetail = addVenteDetail();

		_persistence.remove(newVenteDetail);

		VenteDetail existingVenteDetail = _persistence.fetchByPrimaryKey(
			newVenteDetail.getPrimaryKey());

		Assert.assertNull(existingVenteDetail);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addVenteDetail();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VenteDetail newVenteDetail = _persistence.create(pk);

		newVenteDetail.setIdVente(RandomTestUtil.nextLong());

		newVenteDetail.setIdMedicament(RandomTestUtil.nextLong());

		newVenteDetail.setQuantite(RandomTestUtil.nextInt());

		newVenteDetail.setPrixUnitaire(RandomTestUtil.nextDouble());

		newVenteDetail.setSousTotal(RandomTestUtil.nextDouble());

		_venteDetails.add(_persistence.update(newVenteDetail));

		VenteDetail existingVenteDetail = _persistence.findByPrimaryKey(
			newVenteDetail.getPrimaryKey());

		Assert.assertEquals(
			existingVenteDetail.getIdDetail(), newVenteDetail.getIdDetail());
		Assert.assertEquals(
			existingVenteDetail.getIdVente(), newVenteDetail.getIdVente());
		Assert.assertEquals(
			existingVenteDetail.getIdMedicament(),
			newVenteDetail.getIdMedicament());
		Assert.assertEquals(
			existingVenteDetail.getQuantite(), newVenteDetail.getQuantite());
		AssertUtils.assertEquals(
			existingVenteDetail.getPrixUnitaire(),
			newVenteDetail.getPrixUnitaire());
		AssertUtils.assertEquals(
			existingVenteDetail.getSousTotal(), newVenteDetail.getSousTotal());
	}

	@Test
	public void testCountByVente() throws Exception {
		_persistence.countByVente(RandomTestUtil.nextLong());

		_persistence.countByVente(0L);
	}

	@Test
	public void testCountByMedicament() throws Exception {
		_persistence.countByMedicament(RandomTestUtil.nextLong());

		_persistence.countByMedicament(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		VenteDetail newVenteDetail = addVenteDetail();

		VenteDetail existingVenteDetail = _persistence.findByPrimaryKey(
			newVenteDetail.getPrimaryKey());

		Assert.assertEquals(existingVenteDetail, newVenteDetail);
	}

	@Test(expected = NoSuchVenteDetailException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<VenteDetail> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_VenteDetail", "idDetail", true, "idVente", true,
			"idMedicament", true, "quantite", true, "prixUnitaire", true,
			"sousTotal", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		VenteDetail newVenteDetail = addVenteDetail();

		VenteDetail existingVenteDetail = _persistence.fetchByPrimaryKey(
			newVenteDetail.getPrimaryKey());

		Assert.assertEquals(existingVenteDetail, newVenteDetail);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VenteDetail missingVenteDetail = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingVenteDetail);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		VenteDetail newVenteDetail1 = addVenteDetail();
		VenteDetail newVenteDetail2 = addVenteDetail();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVenteDetail1.getPrimaryKey());
		primaryKeys.add(newVenteDetail2.getPrimaryKey());

		Map<Serializable, VenteDetail> venteDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, venteDetails.size());
		Assert.assertEquals(
			newVenteDetail1, venteDetails.get(newVenteDetail1.getPrimaryKey()));
		Assert.assertEquals(
			newVenteDetail2, venteDetails.get(newVenteDetail2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, VenteDetail> venteDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(venteDetails.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		VenteDetail newVenteDetail = addVenteDetail();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVenteDetail.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, VenteDetail> venteDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, venteDetails.size());
		Assert.assertEquals(
			newVenteDetail, venteDetails.get(newVenteDetail.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, VenteDetail> venteDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(venteDetails.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		VenteDetail newVenteDetail = addVenteDetail();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newVenteDetail.getPrimaryKey());

		Map<Serializable, VenteDetail> venteDetails =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, venteDetails.size());
		Assert.assertEquals(
			newVenteDetail, venteDetails.get(newVenteDetail.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			VenteDetailLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<VenteDetail>() {

				@Override
				public void performAction(VenteDetail venteDetail) {
					Assert.assertNotNull(venteDetail);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		VenteDetail newVenteDetail = addVenteDetail();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VenteDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idDetail", newVenteDetail.getIdDetail()));

		List<VenteDetail> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		VenteDetail existingVenteDetail = result.get(0);

		Assert.assertEquals(existingVenteDetail, newVenteDetail);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VenteDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("idDetail", RandomTestUtil.nextLong()));

		List<VenteDetail> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		VenteDetail newVenteDetail = addVenteDetail();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			VenteDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idDetail"));

		Object newIdDetail = newVenteDetail.getIdDetail();

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
			VenteDetail.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("idDetail"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idDetail", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected VenteDetail addVenteDetail() throws Exception {
		long pk = RandomTestUtil.nextLong();

		VenteDetail venteDetail = _persistence.create(pk);

		venteDetail.setIdVente(RandomTestUtil.nextLong());

		venteDetail.setIdMedicament(RandomTestUtil.nextLong());

		venteDetail.setQuantite(RandomTestUtil.nextInt());

		venteDetail.setPrixUnitaire(RandomTestUtil.nextDouble());

		venteDetail.setSousTotal(RandomTestUtil.nextDouble());

		_venteDetails.add(_persistence.update(venteDetail));

		return venteDetail;
	}

	private List<VenteDetail> _venteDetails = new ArrayList<VenteDetail>();
	private VenteDetailPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}