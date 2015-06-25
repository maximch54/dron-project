package com.dron.sender.controllers.root.models.transformers;

import java.util.List;

import org.springframework.http.HttpHeaders;

import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.pattern.interfaces.IBaseTransformer;

public class HttpHeaderTransformer implements
		IBaseTransformer<HttpHeaders, List<UIHttpHeaders>> {

	@Override
	public List<UIHttpHeaders> transform(final HttpHeaders httpHeaders,
			final List<UIHttpHeaders> uiHttpHeaders) {
		uiHttpHeaders.clear();
		httpHeaders.forEach((key, value) -> {
			uiHttpHeaders.add(new UIHttpHeaders(key, value.get(0)));
		});
		uiHttpHeaders.add(new UIHttpHeaders());
		return uiHttpHeaders;
	}

	@Override
	public HttpHeaders reverseTransform(final HttpHeaders headers,
			final List<UIHttpHeaders> uiHttpHeaders) {
		headers.clear();
		uiHttpHeaders.forEach(uiHttpHeader -> {
			if (!uiHttpHeader.isEmpty()) {
				headers.add(uiHttpHeader.getHeader(), uiHttpHeader.getValue());
			}
		});
		return headers;
	}

}
