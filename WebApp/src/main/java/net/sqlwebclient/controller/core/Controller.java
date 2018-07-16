package net.sqlwebclient.controller.core;

import net.sqlwebclient.controller.request.RequestContext;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Serializable;

public abstract class Controller implements Serializable {
    private static final long serialVersionUID = 7404280070019924868L;

    public void doGet(final RequestContext ctx) throws ServletException, IOException {}

	public void doHead(final RequestContext ctx) throws ServletException, IOException {}

	public void doPut(final RequestContext ctx) throws ServletException, IOException {}

	public void doPost(final RequestContext ctx) throws ServletException, IOException {}

	public void doDelete(final RequestContext ctx) throws ServletException, IOException {}
}
