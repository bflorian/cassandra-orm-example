package cassandra.orm.example

import static org.junit.Assert.*
import org.junit.Test

/**
 * @author: Bob Florian
 */
class UserTests 
{

	@Test
	void testGet()
	{
		def u = User.get("matt")
		assertTrue u instanceof User
		assertEquals "matt@everywhere.com", u.emailAddress
		assertEquals Gender.MALE, u.gender
	}

	@Test
	void testList()
	{
		def list = User.list()
		assertEquals 3, list.size()
		assertNotNull list.find{it.username=='matt'}
		assertNotNull list.find{it.username=='katie'}
		assertNotNull list.find{it.username=='vicky'}
	}

	@Test
	void testFindByPhone()
	{
		def u = User.findByPhone("+34923444121")
		assertEquals "katie", u.username
	}


	@Test
	void testFindByGender()
	{
		def u = User.findByGender(Gender.MALE)
		assertEquals "matt", u.username
	}

	@Test
	void findAllByCountryAndGender()
	{
		def u = User.findAllByCountryAndGender("ESP",Gender.FEMALE)
		assertEquals 1, u.size()
	}

	@Test
	void testFindAllByCountryStateCity()
	{
		def u = User.findAllByCountryAndStateAndCity("USA","MD",["Ellicott City","Olney"])
		assertEquals 2, u.size()
	}


	@Test
	void testSaveMultipleTimes()
	{
		def u = User.get("matt")
		def birthDateCounts = User.getCountsGroupByBirthDate(grain: Calendar.DAY_OF_MONTH)
		def genderCounts = User.getCountsGroupByGender()
		def countryCounts = User.getCountsGroupByCountry()
		def stateCounts = User.getCountsGroupByState()
		def cityCounts = User.getCountsGroupByCity()
		def mdCounts = User.getCountsByCountryAndStateGroupByCity("USA","MD")
		def emailAddressSize = User.countByEmailAddress("katie@everywhere.com")
		def phoneSize = User.findAllByPhone("+13015551212").size()

		for (i in 1..5) {
			u.save()
		}

		assertEquals birthDateCounts, User.getCountsGroupByBirthDate(grain: Calendar.DAY_OF_MONTH)
		assertEquals genderCounts, User.getCountsGroupByGender()
		assertEquals countryCounts, User.getCountsGroupByCountry()
		assertEquals stateCounts, User.getCountsGroupByState()
		assertEquals cityCounts, User.getCountsGroupByCity()
		assertEquals mdCounts, User.getCountsByCountryAndStateGroupByCity("USA","MD")
		assertEquals emailAddressSize,User.countByEmailAddress("katie@everywhere.com")
		assertEquals phoneSize, User.findAllByPhone("+13015551212").size()
	}
}
