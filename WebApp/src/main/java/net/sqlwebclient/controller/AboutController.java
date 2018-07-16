package net.sqlwebclient.controller;

import com.google.inject.Inject;
import net.sqlwebclient.controller.core.Cacheable;
import net.sqlwebclient.controller.core.CtrlResult;
import net.sqlwebclient.controller.core.RequestMapping;
import net.sqlwebclient.controller.request.RequestContext;
import net.sqlwebclient.json.objects.JsonAboutObject;
import net.sqlwebclient.service.AboutService;

@RequestMapping("/api/about")
@Cacheable
final class AboutController extends CrudController {
    private static final long serialVersionUID = -5936396044677341573L;
    private final AboutService aboutService;

    @Inject
    AboutController(final AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @Override
    protected CtrlResult callGet(final RequestContext ctx) {
        return ok(
                JsonAboutObject.of(
                        aboutService.create()
                )
        );
    }
}
