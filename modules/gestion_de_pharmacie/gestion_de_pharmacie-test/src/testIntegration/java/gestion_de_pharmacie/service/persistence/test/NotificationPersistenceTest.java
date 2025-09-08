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

import gestion_de_pharmacie.exception.NoSuchNotificationException;

import gestion_de_pharmacie.model.Notification;

import gestion_de_pharmacie.service.NotificationLocalServiceUtil;
import gestion_de_pharmacie.service.persistence.NotificationPersistence;
import gestion_de_pharmacie.service.persistence.NotificationUtil;

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
public class NotificationPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "gestion_de_pharmacie.service"));

	@Before
	public void setUp() {
		_persistence = NotificationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Notification> iterator = _notifications.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Notification notification = _persistence.create(pk);

		Assert.assertNotNull(notification);

		Assert.assertEquals(notification.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Notification newNotification = addNotification();

		_persistence.remove(newNotification);

		Notification existingNotification = _persistence.fetchByPrimaryKey(
			newNotification.getPrimaryKey());

		Assert.assertNull(existingNotification);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotification();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Notification newNotification = _persistence.create(pk);

		newNotification.setIdUtilisateur(RandomTestUtil.nextLong());

		newNotification.setType(RandomTestUtil.randomString());

		newNotification.setMessage(RandomTestUtil.randomString());

		newNotification.setStatut(RandomTestUtil.randomString());

		newNotification.setDateCreation(RandomTestUtil.nextDate());

		_notifications.add(_persistence.update(newNotification));

		Notification existingNotification = _persistence.findByPrimaryKey(
			newNotification.getPrimaryKey());

		Assert.assertEquals(
			existingNotification.getIdNotification(),
			newNotification.getIdNotification());
		Assert.assertEquals(
			existingNotification.getIdUtilisateur(),
			newNotification.getIdUtilisateur());
		Assert.assertEquals(
			existingNotification.getType(), newNotification.getType());
		Assert.assertEquals(
			existingNotification.getMessage(), newNotification.getMessage());
		Assert.assertEquals(
			existingNotification.getStatut(), newNotification.getStatut());
		Assert.assertEquals(
			Time.getShortTimestamp(existingNotification.getDateCreation()),
			Time.getShortTimestamp(newNotification.getDateCreation()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Notification newNotification = addNotification();

		Notification existingNotification = _persistence.findByPrimaryKey(
			newNotification.getPrimaryKey());

		Assert.assertEquals(existingNotification, newNotification);
	}

	@Test(expected = NoSuchNotificationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Notification> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Pharma_Notification", "idNotification", true, "idUtilisateur",
			true, "type", true, "message", true, "statut", true, "dateCreation",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Notification newNotification = addNotification();

		Notification existingNotification = _persistence.fetchByPrimaryKey(
			newNotification.getPrimaryKey());

		Assert.assertEquals(existingNotification, newNotification);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Notification missingNotification = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotification);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Notification newNotification1 = addNotification();
		Notification newNotification2 = addNotification();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotification1.getPrimaryKey());
		primaryKeys.add(newNotification2.getPrimaryKey());

		Map<Serializable, Notification> notifications =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notifications.size());
		Assert.assertEquals(
			newNotification1,
			notifications.get(newNotification1.getPrimaryKey()));
		Assert.assertEquals(
			newNotification2,
			notifications.get(newNotification2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Notification> notifications =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notifications.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Notification newNotification = addNotification();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotification.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Notification> notifications =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notifications.size());
		Assert.assertEquals(
			newNotification,
			notifications.get(newNotification.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Notification> notifications =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notifications.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Notification newNotification = addNotification();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotification.getPrimaryKey());

		Map<Serializable, Notification> notifications =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notifications.size());
		Assert.assertEquals(
			newNotification,
			notifications.get(newNotification.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Notification>() {

				@Override
				public void performAction(Notification notification) {
					Assert.assertNotNull(notification);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Notification newNotification = addNotification();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Notification.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idNotification", newNotification.getIdNotification()));

		List<Notification> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		Notification existingNotification = result.get(0);

		Assert.assertEquals(existingNotification, newNotification);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Notification.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"idNotification", RandomTestUtil.nextLong()));

		List<Notification> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Notification newNotification = addNotification();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Notification.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idNotification"));

		Object newIdNotification = newNotification.getIdNotification();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idNotification", new Object[] {newIdNotification}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIdNotification = result.get(0);

		Assert.assertEquals(existingIdNotification, newIdNotification);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Notification.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("idNotification"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"idNotification", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Notification addNotification() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Notification notification = _persistence.create(pk);

		notification.setIdUtilisateur(RandomTestUtil.nextLong());

		notification.setType(RandomTestUtil.randomString());

		notification.setMessage(RandomTestUtil.randomString());

		notification.setStatut(RandomTestUtil.randomString());

		notification.setDateCreation(RandomTestUtil.nextDate());

		_notifications.add(_persistence.update(notification));

		return notification;
	}

	private List<Notification> _notifications = new ArrayList<Notification>();
	private NotificationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}