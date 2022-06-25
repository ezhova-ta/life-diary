package com.example.lifediary.domain.utils.searchers

import com.example.lifediary.domain.models.PostAddress

class PostAddressListItemSearcher : ListItemSearcher<PostAddress> {
	override fun search(list: List<PostAddress>, searchQuery: String): List<PostAddress> {
		return list.filter { postAddress ->
			postAddress.name.contains(searchQuery, true)
		}
	}

}