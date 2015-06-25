package com.dron.sender.controllers.root.models.transformers;

import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.controllers.root.models.UISequence;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

public class SequenceTransformer implements
		IBaseTransformer<Sequence, UISequence> {

	@Override
	public UISequence transform(final Sequence sequence,
			final UISequence uiSequence) {

		uiSequence.setSelectedPluginId(sequence.getSelectedPluginId());

		uiSequence.getOrder().clear();
		sequence.getOrder().forEach(o -> uiSequence.getOrder().add(o));

		TransformerFactory.transformEntity(sequence.getParams(),
				uiSequence.getUIParams(), TransformKey.ROOT_PARAMS);

		uiSequence.getKeys().clear();
		uiSequence.getUIParams().forEach(
				param -> uiSequence.getKeys().add(param.getKey()));

		uiSequence.getUiPlugins().clear();
		sequence.getPlugins().forEach(
				plugin -> {
					UIPlugin uiPlugin = new UIPlugin();
					TransformerFactory.transformEntity(plugin, uiPlugin,
							TransformKey.ROOT_PLUGIN);
					uiSequence.getUiPlugins().add(uiPlugin);
				});
		uiSequence.getSentPlugins().clear();
		sequence.getSentPlugins().forEach(
				plugin -> {
					UIPlugin uiPlugin = new UIPlugin();
					TransformerFactory.transformEntity(plugin, uiPlugin,
							TransformKey.ROOT_PLUGIN);
					uiSequence.getSentPlugins().add(uiPlugin);
				});

		return uiSequence;
	}

	@Override
	public Sequence reverseTransform(final Sequence sequence,
			final UISequence uiSequence) {
		sequence.setSelectedPluginId(uiSequence.getSelectedPluginId());

		sequence.getOrder().clear();
		uiSequence.getOrder().forEach(o -> sequence.getOrder().add(o));
		
		sequence.getSentPlugins().clear();
		
		TransformerFactory.reverseTransformEntity(sequence.getParams(),
				uiSequence.getUIParams(), TransformKey.ROOT_PARAMS);

		sequence.getPlugins().clear();
		uiSequence.getUiPlugins().forEach(
				uiPlugin -> {
					Plugin plugin = new Plugin();
					TransformerFactory.reverseTransformEntity(plugin, uiPlugin,
							TransformKey.ROOT_PLUGIN);
					sequence.getPlugins().add(plugin);
				});

		return sequence;
	}
}
