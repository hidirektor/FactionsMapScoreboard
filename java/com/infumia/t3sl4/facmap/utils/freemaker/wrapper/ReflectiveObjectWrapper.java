package com.infumia.t3sl4.facmap.utils.freemaker.wrapper;

import freemarker.ext.beans.BeansWrapperConfiguration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.DefaultObjectWrapperConfiguration;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;
import java.util.UUID;

public class ReflectiveObjectWrapper extends DefaultObjectWrapper {
   public ReflectiveObjectWrapper(Version incompatibleImprovements) {
      super(incompatibleImprovements);
   }

   public ReflectiveObjectWrapper(BeansWrapperConfiguration bwCfg, boolean writeProtected) {
      super(bwCfg, writeProtected);
   }

   public ReflectiveObjectWrapper(DefaultObjectWrapperConfiguration dowCfg, boolean writeProtected) {
      super(dowCfg, writeProtected);
   }

   public TemplateModel wrap(Object obj) throws TemplateModelException {
      return (TemplateModel)(obj instanceof UUID ? new UUIDModel((UUID)obj) : super.wrap(obj));
   }
}
