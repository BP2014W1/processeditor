package de.uni_potsdam.hpi.bpt.bp2014.jeditor.converter.adapter.acpm;

import de.uni_potsdam.hpi.bpt.bp2014.conversion.IEdge;
import de.uni_potsdam.hpi.bpt.bp2014.conversion.INode;
import de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.*;
import de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.Event;
import de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.Gateway;
import net.frapu.code.visualization.ProcessEdge;
import net.frapu.code.visualization.ProcessModel;
import net.frapu.code.visualization.ProcessNode;
import net.frapu.code.visualization.bpmn.*;
import net.frapu.code.visualization.bpmn.DataObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Stpehan on 07.05.2015.
 */
public class ACPMAdapter extends ActivityCentricProcessModel {

    private ProcessModel model;
    private Map<ProcessNode, INode> nodes;
    private INode finalNode;
    private Collection<IEdge> edges;

    public ACPMAdapter(ProcessModel model) {
        super();
        this.model = model;
        initialize();
    }

    private INode wrapNode(ProcessNode raw) {
        INode node = null;
        if (nodes.containsKey(raw)) {
            return nodes.get(raw);
        }
        for (ProcessNode processNode : model.getNodes()) {
            if (processNode instanceof Task) {
                node = new ActivityAdapter((Task) processNode);
            } else if (processNode instanceof EndEvent) {
                node = new EventAdapter((net.frapu.code.visualization.bpmn.Event) processNode);
                ((Event)node).setType(Event.Type.END);
            } else if (processNode instanceof StartEvent) {
                node = new EventAdapter((net.frapu.code.visualization.bpmn.Event) processNode);
                ((Event)node).setType(Event.Type.START);
            } else if (processNode instanceof ExclusiveGateway) {
                node = new GatewayAdapter((net.frapu.code.visualization.bpmn.Gateway) processNode);
                ((Gateway)node).setType(Gateway.Type.XOR);
            } else if (processNode instanceof ParallelGateway) {
                node = new GatewayAdapter((net.frapu.code.visualization.bpmn.Gateway) processNode);
                ((Gateway)node).setType(Gateway.Type.AND);
            }
        }
        return node;
    }

    private void initialize() {
        edges = new HashSet<>();
        nodes = new HashMap<>();
        for (ProcessEdge processEdge : model.getEdges()) {
            if (processEdge instanceof SequenceFlow) {
                INode source = wrapNode(processEdge.getSource());
                INode target = wrapNode(processEdge.getTarget());
                ControlFlow cf = new ControlFlowAdapter(source, target, processEdge);
                edges.add(cf);
                source.addOutgoingEdge(cf);
                target.addOutgoingEdge(cf);
            } else if (processEdge instanceof MessageFlow) {
                INode source = wrapNode(processEdge.getSource());
                INode target = wrapNode(processEdge.getTarget());
                DataFlow df;
                if (source instanceof de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.Activity) {
                    df = new DataFlowAdapter((de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.Activity)source, (de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.DataObject)target, processEdge);
                } else {
                    df =new DataFlowAdapter((de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.DataObject)source, (de.uni_potsdam.hpi.bpt.bp2014.conversion.activity_centric.Activity)target, processEdge);
                }
                edges.add(df);
                source.addOutgoingEdge(df);
                target.addIncomingEdge(df);
            }
        }
    }
}
