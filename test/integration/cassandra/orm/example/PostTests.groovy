package cassandra.orm.example

import static org.junit.Assert.*
import org.junit.Test

/**
 * @author: Bob Florian
 */
class PostTests 
{
	@Test
	void testGetCountsByUser()
	{
		def u = User.get("matt")
		def c = Post.getCountsByUserGroupByOccurTime(u, [grain: Calendar.MONTH])
		println c
		assertNotNull c["2012-07"]
	}

}
