package com.example.lifediary.ui.post_addresses

import com.example.lifediary.data.domain.PostAddress
import com.example.lifediary.ui.common.ListItemSearcher

class PostAddressListItemSearcher : ListItemSearcher<PostAddress> {
	override fun search(list: List<PostAddress>, searchQuery: String): List<PostAddress> {
		return list.filter { postAddress ->
			postAddress.name.contains(searchQuery, true)
		}
	}

}