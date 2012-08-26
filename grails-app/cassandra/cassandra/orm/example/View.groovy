package cassandra.orm.example

/**
 * @author: Bob Florian
 */
class View 
{
	UUID uuid
	Date occurTime
	ReferrerType referrerType
	String referrerName
	String ipAddress

	Post post
	static belongsTo = [post: Post]

	static cassandraMapping = [
			primaryKey: 'uuid',
			timeToLive: [ipAddress: 30],
			counters: [
					[groupBy: ['occurTime']],
					[groupBy: ['occurTime','referrerType']],
					[groupBy: ['occurTime','referrerType','referrerName']],
					[findBy:  ['post'], groupBy:['occurTime']],
			]
	]
}
