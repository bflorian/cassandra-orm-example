package cassandra.orm.example

/**
 * @author: Bob Florian
 */
class User 
{
	String username
	String emailAddress
	String firstName
	String lastName
	String address
	String city
	String state
	String zip
	String country
	String phone
	Gender gender
	Date birthDate

	List posts
	static hasMany = [posts: Post]

	static cassandraMapping = [
	        primaryKey: 'username',
			explicitIndexes: ["emailAddress", "phone", 'birthDay', ["country","state","city"]],
			secondaryIndexes: ["gender","country", "state"],
			counters: [
					[groupBy: ['birthDate']],
					[groupBy: ['gender']],
					[groupBy: ['country','state','city','gender']],
					[findBy:  ['country','state'], groupBy:['city','gender']]
			]
	]

	static transitents = ['birthDay']

	String getBirthDay()
	{
		birthDate?.format("MM-dd")
	}
}
