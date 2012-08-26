package cassandra.orm.example

/**
 * @author: Bob Florian
 */
class Comment 
{
	UUID uuid
	String text
	Date occurTime

	Post post
	Post user
	static belongsTo = [post: Post, user: User]

	static cassandraMapping = [
			primaryKey: 'uuid',
			counters: [
					[groupBy: ['occurTime']],
					[findBy:  ['user'], groupBy:['occurTime']],
					[findBy:  ['post'], groupBy:['occurTime']]
			]
	]
}
